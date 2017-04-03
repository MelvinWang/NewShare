package com.melvin.share.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.DateUtil;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
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
    private boolean fromAllOrder;
    private String orderId;

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
            makeSureOrder();
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
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 确认订单
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
        final PaySuccessDialog dialog = new PaySuccessDialog(mContext);
        dialog.setContentView(null);
        dialog.show();
        pay();


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
