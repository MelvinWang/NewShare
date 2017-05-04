package com.melvin.share.ui.activity.order;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.DateUtil;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.alipay.OrderInfoUtil2_0;
import com.melvin.share.alipay.PayResult;
import com.melvin.share.databinding.ActivityWaitpayOrderBinding;
import com.melvin.share.dialog.OrderCancelDialog;
import com.melvin.share.dialog.PaySuccessDialog;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.modelview.acti.WaitPayOrderViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxPayBus;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.wxapi.Constants;
import com.melvin.share.wxapi.MD5;
import com.melvin.share.wxapi.UtilPay;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/1
 * <p>
 * 描述： 订单信息 未付款
 */
public class WaitPayOrderActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private ActivityWaitpayOrderBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private WaitPayOrderViewModel waitPayOrderViewModel;
    private String name;
    private String phone;
    private String address;
    private String cartIds;
    private String stockId;
    private String postage;
    private String totalNum;
    private boolean fromAllOrder;
    private boolean fromCat;
    private String orderId;
    private String payTotalFee = "0.00";
    private String payTitle = "";
    private String payOrderNumber = "";
    private String payBody = "";
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017022005784103";
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJOruJBmvGT/9qLMDZ16Gl5GCyZudWbuWFdFv0SOR/yg5VpXEH0VK0ddgXSB/P3zJ3DiBiCbN5ac8irVP/WGzQJlfPrec4ZGVJ5PiQUPKbBFgCLNVhTG66/JFQmD4t0qFiOoT4lH3lJbUXvZDEhNh/lZPFxnku++eIesoATpFOonmmttxS/4lL9egAAwjTVSozxTW7FY4IWULd+ZOkWK30OH4CihcztEqCWm872jrHK3a2gZ1FcALa+12S1m5vpuDODhBPWcgxVF3l65CVhRazq8ImacqlAibsKqNDXK9puJvD4rfPD4cs11HHLefYklcMrGo02cjtW/5Rem7ZvM0tAgMBAAECggEAIRpS3LX4z4jCoxkSmDoE22Cg1sgt69KqeSvddqsSu6TBGnXjMzCSU8fNjagvTFSZ9tAojZ2rdpFIjyPijEkHTprBOe7IRPkq2c1rDF0KeMxucm/gNS1b1K1SXG+tKB6UIX7eTpx7Vghni8eFoXIuS3CIif/bgbLwYiLEkZhiQuZgIw15J6H6NT756biuqim468lPG0AKhbr81yDrLdSGAKmTlI7ogbGsU7nIpSwexaqqMIUkp9flt2lswI0Xr3Ce0vSdzBOgQvSW+QFvo2fiWkc/zIJNaL+7xR9uM+BwPhC+bdnbHp7y1sTZoLmYEnm3K950u/EZPj2ibP4YUo87vQKBgQDnY9wPbrKCCc0xbrrVi94fEcusQc1QVcIKYI6XNI5JCIp0iOGxbnl2GSUGIT5Y0umzrAl2Dg4Lit5xKPOKsP3N9pwcE6ThQsdBSL3Qqfo0Di5xNjFAPqqsGBTIv+wIkzsYO1vFtXbxQAJnhvRSnprHIvKsqzTmmmkOI+7+b4c31wKBgQCX0yCOyet7ga6xqnP27eCbNmgwB3DbhXOifUrSOYw/PFyY0PKw/qogQQPaYHh8Jnca3tKv53381HFBbcqYiNAQFFMoiua5MUlHd9Bqz4UG+0nBY3uyEpJJFb74oMWdtn4WuIkh3M4lSf8weNEjrYaiwKYvb1lOkh79SMoibvoymwKBgQCUu72iNdeMetxon/J8fZFjiz7OFOXyc41sujAtxVyIWXMWAT6BnK96WCNSU2AOHFSYZ0cVmWcxyCu/tUEGr0oVcCVcbswW96O4tRc932SyvULhBqKV1zJBuKDzgVopEOp1YwJUjZZM8IYbIcY+rTB0PGSc1NgGOs64hiSDbBEQtQKBgAMHt7Kh3Q1N/UqWeFIJVV5Dq7iprVr0QuaPUZr4gxAQCFkUcoON0Z9rLMSDAGSZ7+6dW98e46jUJJ6FrnF06ZIolbBNdWVk0m2WupYnJiSJh2NQtPtM25aR1Inpy4fmSIXzkOkYGxUrcOhqwOHRaMp+xdJUipfC2tN7TG17gYrZAoGAVHVB1uV8sq2xU+783mCYd1Bhoq/pOSOFyY7ivVUbwXRW+usSQEN3mzIQ2dEBsWQfOBAWu04eIp1TGShK35wyAqpeSpsU8mLCskWzOA/Q4aGlwOItu8GsAj9Zfc4OgLy0FbsrT8k2PScgzxwcxLFeWZWA+hrMvtIbEvLRVSdHuZI=";
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    //appid 微信分配的公众账号ID
    public static final String APP_ID = Constants.APP_ID;
    //商户号 微信分配的公众账号ID
    public static final String MCH_ID = Constants.MCH_ID;
    //  API密钥，在商户平台设置
    public static final String API_KEY = Constants.API_KEY;
    PayReq req;
    IWXAPI msgApi;
    Map<String, String> resultunifiedorder;
    StringBuffer sb;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_waitpay_order);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        RxPayBus.get().register(this);
        msgApi = WXAPIFactory.createWXAPI(this, null);
        req = new PayReq();
        sb = new StringBuffer();
        ininData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxPayBus.get().unregister(this);
    }

    @Subscribe
    public void wechatPayFlag(String flag) {
        if (TextUtils.equals("1",flag)) {
            final PaySuccessDialog dialog = new PaySuccessDialog(mContext);
            dialog.setContentView(null);
            dialog.show();
            startActivity(new Intent(mContext, AllOrderActivity.class));
            finish();
        } else {
            Utils.showToast(mContext, "支付失败");
            finish();
        }
    }

    private void ininData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
        cartIds = intent.getStringExtra("cartIds");
        stockId = intent.getStringExtra("stockId");
        postage = intent.getStringExtra("postage");
        postage = intent.getStringExtra("postage");
        totalNum = intent.getStringExtra("totalNum");
        fromCat = intent.getBooleanExtra("fromCat", true);

        //是否从全部订单页面过来，true代表是
        fromAllOrder = intent.getBooleanExtra("fromAllOrder", false);
        orderId = intent.getStringExtra("orderId");
        mRecyclerView = binding.recyclerView;
        binding.aliPay.setOnCheckedChangeListener(this);
        binding.wechatPay.setOnCheckedChangeListener(this);

        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        waitPayOrderViewModel = new WaitPayOrderViewModel(this, mRecyclerView);
        binding.setViewModel(waitPayOrderViewModel);
        if (fromAllOrder) {
            findOrderById();
        } else {
            if (fromCat) {
                makeSureOrder();
            } else {
                makeOtherSureOrder();
            }
        }


    }

    /**
     * 查看单个订单的详情
     */
    private void findOrderById() {
        fromNetwork.findOrderById(orderId)
                .compose(new RxActivityHelper<WaitPayOrderInfo.OrderBean>().ioMain(WaitPayOrderActivity.this, true))
                .subscribe(new RxModelSubscribe<WaitPayOrderInfo.OrderBean>(mContext, true) {
                    @Override
                    protected void myNext(WaitPayOrderInfo.OrderBean bean) {
                        binding.name.setText(bean.receiver);
                        binding.phone.setText(bean.recevierPhone);
                        binding.address.setText(bean.receiveAddress);

                        binding.freight.setText("￥ " + bean.postage);
                        binding.totalFee.setText("￥ " + bean.total);

                        binding.orderNumber.setText(bean.orderNumber);
                        binding.createTime.setText(DateUtil.getDateString(bean.createTime));
                        waitPayOrderViewModel.requestData(bean.orderItemResponses);

                        payTotalFee = bean.total;
                        payTitle = bean.orderItemResponses.get(0).productName;
                        payBody = bean.orderItemResponses.get(0).productName;
                        payOrderNumber = bean.orderNumber;
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 确认订单 购物车
     */
    private void makeSureOrder() {
        Map orderMap = new HashMap();
        orderMap.put("receiver", name);
        orderMap.put("recevierPhone", phone);
        orderMap.put("receiveAddress", address);
        orderMap.put("cartIds", cartIds);
        ShapreUtils.putParamCustomerId(orderMap);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(orderMap)));
        fromNetwork.makeSureOrder(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel<WaitPayOrderInfo>>().ioMain(WaitPayOrderActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel<WaitPayOrderInfo>>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel<WaitPayOrderInfo> commonReturnModel) {
                        binding.name.setText(commonReturnModel.result.order.receiver);
                        binding.phone.setText(commonReturnModel.result.order.recevierPhone);
                        binding.address.setText(commonReturnModel.result.order.receiveAddress);

                        binding.freight.setText("￥ " + commonReturnModel.result.order.postage);
                        binding.totalFee.setText("￥ " + commonReturnModel.result.order.total);

                        payTotalFee = commonReturnModel.result.order.total;
                        payTitle = commonReturnModel.result.order.orderItemResponses.get(0).productName;
                        payBody = commonReturnModel.result.order.orderItemResponses.get(0).productName;
                        payOrderNumber = commonReturnModel.result.order.orderNumber;

                        binding.orderNumber.setText(commonReturnModel.result.order.orderNumber);
                        binding.createTime.setText(DateUtil.getDateString(commonReturnModel.result.order.createTime));

                        waitPayOrderViewModel.requestData(commonReturnModel.result.order.orderItemResponses);

                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 确认订单 直接购买
     */
    private void makeOtherSureOrder() {
        Map orderMap = new HashMap();
        orderMap.put("receiver", name);
        orderMap.put("recevierPhone", phone);
        orderMap.put("receiveAddress", address);
        orderMap.put("totalNum", totalNum);
        orderMap.put("stockId", stockId);
        orderMap.put("postage", postage);
        ShapreUtils.putParamCustomerId(orderMap);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(orderMap)));
        fromNetwork.directBuyProduct(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel<WaitPayOrderInfo>>().ioMain(WaitPayOrderActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel<WaitPayOrderInfo>>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel<WaitPayOrderInfo> commonReturnModel) {
                        binding.name.setText(commonReturnModel.result.order.receiver);
                        binding.phone.setText(commonReturnModel.result.order.recevierPhone);
                        binding.address.setText(commonReturnModel.result.order.receiveAddress);

                        binding.freight.setText("￥ " + commonReturnModel.result.order.postage);
                        binding.totalFee.setText("￥ " + commonReturnModel.result.order.total);

                        binding.orderNumber.setText(commonReturnModel.result.order.orderNumber);
                        binding.createTime.setText(DateUtil.getDateString(commonReturnModel.result.order.createTime));

                        waitPayOrderViewModel.requestData(commonReturnModel.result.order.orderItemResponses);
                        payTotalFee = commonReturnModel.result.order.total;
                        payTitle = commonReturnModel.result.order.orderItemResponses.get(0).productName;
                        payBody = commonReturnModel.result.order.orderItemResponses.get(0).productName;
                        payOrderNumber = commonReturnModel.result.order.orderNumber;
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 支付宝及微信支付
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        switch (id) {
            case R.id.ali_pay:
                if (isChecked && binding.wechatPay.isChecked()) {
                    binding.wechatPay.setChecked(false);
                }
                break;
            case R.id.wechat_pay:
                if (isChecked && binding.aliPay.isChecked()) {
                    binding.aliPay.setChecked(false);
                }
                break;
        }
    }

    /**
     * 付款按钮
     *
     * @param view
     */
    public void goToPay(View view) {
        if (binding.aliPay.isChecked()) {
            aliPay();
        } else if (binding.wechatPay.isChecked()) {
            wechatPay();
        } else {
            Utils.showToast(mContext, "请选择支付方式");
        }


    }

    /**
     * 微信
     */
    private void wechatPay() {
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
    }

    /**
     * 生成签名
     */

    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(API_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        LogUtils.i(packageSign);
        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(API_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        LogUtils.i("orionsign----" + appSign);

        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        return sb.toString();
    }

    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(mContext, getString(R.string.app_tip), getString(R.string.getting_prepayid));
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            LogUtils.i("oriononPreExecute----" + result.toString());
            resultunifiedorder = result;
            genPayReq();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();
            byte[] buf = UtilPay.httpPost(url, entity);
            String content = new String(buf);
            Map<String, String> xml = decodeXml(content);
            return xml;
        }
    }


    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", "----" + e.toString());
        }
        return null;

    }


    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


    private String genOutTradNo() {
        Random random = new Random();
//		return "COATBAE810"; //订单号写死的话只能支付一次，第二次不能生成订单
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }


    //
    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();
        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", APP_ID));
            packageParams.add(new BasicNameValuePair("body", payTitle));
            packageParams.add(new BasicNameValuePair("mch_id", MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", "http://139.129.205.111/common/pay/bpayByWechat"));
            packageParams.add(new BasicNameValuePair("out_trade_no", payOrderNumber));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));

            int wechatTotalFee = (int) (Double.parseDouble(payTotalFee) * 100);
            packageParams.add(new BasicNameValuePair("total_fee", wechatTotalFee + ""));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));

            String xmlstring = toXml(packageParams);

            return new String(xmlstring.getBytes(), "ISO8859-1");

        } catch (Exception e) {
            return null;
        }


    }

    private void genPayReq() {
        req.appId = APP_ID;
        req.partnerId = MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "prepay_id=" + resultunifiedorder.get("prepay_id");
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");
        LogUtils.i("orion----" + signParams.toString());
        sendPayReq();

    }

    private void sendPayReq() {
        msgApi.registerApp(APP_ID);
        msgApi.sendReq(req);
    }


    /**
     * 支付宝
     */
    private void aliPay() {
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, payTotalFee, payOrderNumber, payTitle, payBody, DateUtil.getCurrDateTime());
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Map<String, String>>() {
                    @Override
                    public Map<String, String> call(String s) {
                        PayTask alipay = new PayTask(WaitPayOrderActivity.this);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Map<String, String>>() {
                    @Override
                    public void call(Map<String, String> map) {
                        PayResult payResult = new PayResult(map);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            final PaySuccessDialog dialog = new PaySuccessDialog(mContext);
                            dialog.setContentView(null);
                            dialog.show();
                            startActivity(new Intent(mContext, AllOrderActivity.class));
                            finish();
                        } else {
                            Toast.makeText(WaitPayOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void pay() {
        Map payMap = new HashMap();
        payMap.put("id", orderId);
        ShapreUtils.putParamCustomerId(payMap);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(payMap)));
        fromNetwork.updateOrderStatus(jsonObject)
                .compose(new RxActivityHelper<WaitPayOrderInfo.OrderBean>().ioMain(WaitPayOrderActivity.this, true))
                .subscribe(new RxModelSubscribe<WaitPayOrderInfo.OrderBean>(mContext, true) {
                    @Override
                    protected void myNext(WaitPayOrderInfo.OrderBean bean) {
                        binding.name.setText(bean.receiver);
                        binding.phone.setText(bean.recevierPhone);
                        binding.address.setText(bean.receiveAddress);

                        binding.freight.setText("￥ " + bean.postage);
                        binding.totalFee.setText("￥ " + bean.total);

                        binding.orderNumber.setText(bean.orderNumber);
                        binding.createTime.setText(DateUtil.getDateString(bean.createTime));
                        waitPayOrderViewModel.requestData(bean.orderItemResponses);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 取消按钮
     *
     * @param view
     */
    public void cancelPay(View view) {
        final OrderCancelDialog dialog = new OrderCancelDialog(mContext);
        dialog.setContentView(null);
        dialog.show();
        dialog.setOnClickListener(new OrderCancelDialog.OnCliclListener() {
            @Override
            public void confirm() {
                Utils.showToast(mContext, "confirm");
            }

            @Override
            public void cancel() {

            }
        });
    }
}
