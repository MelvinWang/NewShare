package com.melvin.share.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.DateUtil;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.alipay.OrderInfoUtil2_0;
import com.melvin.share.alipay.PayDemoActivity;
import com.melvin.share.alipay.PayResult;
import com.melvin.share.databinding.ActivityConfirmOrderBinding;
import com.melvin.share.databinding.ActivityWaitpayOrderBinding;
import com.melvin.share.dialog.ConfirmReceiveDialog;
import com.melvin.share.dialog.OrderCancelDialog;
import com.melvin.share.dialog.PaySuccessDialog;
import com.melvin.share.model.Product;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.modelview.acti.ConfirmOrderViewModel;
import com.melvin.share.modelview.acti.WaitPayOrderViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.selfcenter.ManageAddressActivity;
import com.melvin.share.ui.activity.shopcar.ShoppingCarActivity;
import com.melvin.share.view.MyRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.melvin.share.R.id.number;

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
    private String payTotalFee="0.00";
    private String payTitle="";
    private String payOrderNumber="";
    private String payBody="";
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017022005784103";
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJOruJBmvGT/9qLMDZ16Gl5GCyZudWbuWFdFv0SOR/yg5VpXEH0VK0ddgXSB/P3zJ3DiBiCbN5ac8irVP/WGzQJlfPrec4ZGVJ5PiQUPKbBFgCLNVhTG66/JFQmD4t0qFiOoT4lH3lJbUXvZDEhNh/lZPFxnku++eIesoATpFOonmmttxS/4lL9egAAwjTVSozxTW7FY4IWULd+ZOkWK30OH4CihcztEqCWm872jrHK3a2gZ1FcALa+12S1m5vpuDODhBPWcgxVF3l65CVhRazq8ImacqlAibsKqNDXK9puJvD4rfPD4cs11HHLefYklcMrGo02cjtW/5Rem7ZvM0tAgMBAAECggEAIRpS3LX4z4jCoxkSmDoE22Cg1sgt69KqeSvddqsSu6TBGnXjMzCSU8fNjagvTFSZ9tAojZ2rdpFIjyPijEkHTprBOe7IRPkq2c1rDF0KeMxucm/gNS1b1K1SXG+tKB6UIX7eTpx7Vghni8eFoXIuS3CIif/bgbLwYiLEkZhiQuZgIw15J6H6NT756biuqim468lPG0AKhbr81yDrLdSGAKmTlI7ogbGsU7nIpSwexaqqMIUkp9flt2lswI0Xr3Ce0vSdzBOgQvSW+QFvo2fiWkc/zIJNaL+7xR9uM+BwPhC+bdnbHp7y1sTZoLmYEnm3K950u/EZPj2ibP4YUo87vQKBgQDnY9wPbrKCCc0xbrrVi94fEcusQc1QVcIKYI6XNI5JCIp0iOGxbnl2GSUGIT5Y0umzrAl2Dg4Lit5xKPOKsP3N9pwcE6ThQsdBSL3Qqfo0Di5xNjFAPqqsGBTIv+wIkzsYO1vFtXbxQAJnhvRSnprHIvKsqzTmmmkOI+7+b4c31wKBgQCX0yCOyet7ga6xqnP27eCbNmgwB3DbhXOifUrSOYw/PFyY0PKw/qogQQPaYHh8Jnca3tKv53381HFBbcqYiNAQFFMoiua5MUlHd9Bqz4UG+0nBY3uyEpJJFb74oMWdtn4WuIkh3M4lSf8weNEjrYaiwKYvb1lOkh79SMoibvoymwKBgQCUu72iNdeMetxon/J8fZFjiz7OFOXyc41sujAtxVyIWXMWAT6BnK96WCNSU2AOHFSYZ0cVmWcxyCu/tUEGr0oVcCVcbswW96O4tRc932SyvULhBqKV1zJBuKDzgVopEOp1YwJUjZZM8IYbIcY+rTB0PGSc1NgGOs64hiSDbBEQtQKBgAMHt7Kh3Q1N/UqWeFIJVV5Dq7iprVr0QuaPUZr4gxAQCFkUcoON0Z9rLMSDAGSZ7+6dW98e46jUJJ6FrnF06ZIolbBNdWVk0m2WupYnJiSJh2NQtPtM25aR1Inpy4fmSIXzkOkYGxUrcOhqwOHRaMp+xdJUipfC2tN7TG17gYrZAoGAVHVB1uV8sq2xU+783mCYd1Bhoq/pOSOFyY7ivVUbwXRW+usSQEN3mzIQ2dEBsWQfOBAWu04eIp1TGShK35wyAqpeSpsU8mLCskWzOA/Q4aGlwOItu8GsAj9Zfc4OgLy0FbsrT8k2PScgzxwcxLFeWZWA+hrMvtIbEvLRVSdHuZI=";
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_waitpay_order);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
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

                        payTotalFee=(Double.parseDouble( bean.postage)+
                                Double.parseDouble(bean.total))+"";
                        payTitle=bean.orderItemResponses.get(0).productName;
                        payBody=bean.orderItemResponses.get(0).productName;
                        payOrderNumber=bean.orderNumber;
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

                        payTotalFee=(Double.parseDouble(commonReturnModel.result.order.postage)+
                                Double.parseDouble(commonReturnModel.result.order.total))+"";
                        payTitle=commonReturnModel.result.order.orderItemResponses.get(0).productName;
                        payBody=commonReturnModel.result.order.orderItemResponses.get(0).productName;
                        payOrderNumber=commonReturnModel.result.order.orderNumber;

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
                        payTotalFee=(Double.parseDouble(commonReturnModel.result.order.postage)+
                                Double.parseDouble(commonReturnModel.result.order.total))+"";
                        payTitle=commonReturnModel.result.order.orderItemResponses.get(0).productName;
                        payBody=commonReturnModel.result.order.orderItemResponses.get(0).productName;
                        payOrderNumber=commonReturnModel.result.order.orderNumber;
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
            Utils.showToast(mContext, "请先使用支付宝支付");
        } else {
            Utils.showToast(mContext, "请选择支付方式");
        }


    }

    /**
     * 支付宝
     */
    private void aliPay() {
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,"0.01",payOrderNumber,payTitle,payBody,DateUtil.getCurrDateTime());
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
//                            startActivity(new Intent(mContext,AllOrderActivity.class));
//                            finish();
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
