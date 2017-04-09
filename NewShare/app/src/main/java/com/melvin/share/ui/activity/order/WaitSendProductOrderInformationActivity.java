package com.melvin.share.ui.activity.order;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.R;
import com.melvin.share.Utils.DateUtil;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityWaitSendProdOrderInfoBinding;
import com.melvin.share.dialog.ConfirmReceiveDialog;
import com.melvin.share.dialog.UrgeBillDialog;
import com.melvin.share.model.AllOrderBusFlag;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.modelview.acti.WaitSendProOrderInfoViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxCommonBus;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxOrderBus;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/3
 * <p>
 * 描述： 待发货、收货、评价订单信息
 */
public class WaitSendProductOrderInformationActivity extends BaseActivity {

    private ActivityWaitSendProdOrderInfoBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private WaitSendProOrderInfoViewModel waitSendProOrderInfoViewModel;
    private String orderId;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wait_sendpro_order_info);
        mContext = this;
        RxOrderBus.get().register(this);
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxOrderBus.get().unregister(this);
    }

    private void ininData() {
        orderId = getIntent().getStringExtra("orderId");
        LogUtils.i("orderId" + orderId);
        mRecyclerView = binding.recyclerView;
        waitSendProOrderInfoViewModel = new WaitSendProOrderInfoViewModel(this, mRecyclerView);
        binding.setViewModel(waitSendProOrderInfoViewModel);
        findOrderById();
    }

    /**
     * 查看单个订单的详情
     */
    private void findOrderById() {
        fromNetwork.findOrderById(orderId)
                .compose(new RxActivityHelper<WaitPayOrderInfo.OrderBean>().ioMain(WaitSendProductOrderInformationActivity.this, true))
                .subscribe(new RxModelSubscribe<WaitPayOrderInfo.OrderBean>(mContext, true) {
                    @Override
                    protected void myNext(WaitPayOrderInfo.OrderBean bean) {
                        statusVisibility(bean);


                        binding.name.setText(bean.receiver);
                        binding.phone.setText(bean.recevierPhone);
                        binding.address.setText(bean.receiveAddress);

                        binding.freight.setText("￥ " + bean.postage);
                        binding.totalFee.setText("￥ " + bean.total);

                        binding.orderNumber.setText(bean.orderNumber);
                        binding.createTime.setText(DateUtil.getDateString(bean.createTime));
                        waitSendProOrderInfoViewModel.requestData(bean.orderItemResponses);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 判断状态以及显示按钮
     *
     * @param bean
     */
    private void statusVisibility(WaitPayOrderInfo.OrderBean bean) {
        if (TextUtils.equals("2", bean.orderStatus)) {//待发货
            binding.statusImg.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.wait_send));
        } else if (TextUtils.equals("3", bean.orderStatus)) {//待收货
            binding.statusImg.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.wait_receive));
        } else if (TextUtils.equals("3", bean.orderStatus)) {//待评价
            binding.statusImg.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.wait_evaluatei));
        }

        if (TextUtils.equals("2", bean.orderStatus) || TextUtils.equals("3", bean.orderStatus)) {//待发货  待收货
            binding.refund.setVisibility(View.VISIBLE);
        }
        if (TextUtils.equals("2", bean.orderStatus)) {//待发货
            binding.reminder.setVisibility(View.VISIBLE);
        }
        if (TextUtils.equals("3", bean.orderStatus)) {// 待收货
            binding.confirmReceive.setVisibility(View.VISIBLE);
        }

    }


    @Subscribe
    public void getCode(AllOrderBusFlag allOrderBusFlag) {
        if (allOrderBusFlag.flagId == 1) {//单个商品订单确认收货
            makeSureOrderItemReceived(allOrderBusFlag);
        }

    }

    //单个商品订单确认收货
    private void makeSureOrderItemReceived(AllOrderBusFlag allOrderBusFlag) {
        Map map = new HashMap();
        map.put("orderItemId", allOrderBusFlag.id);
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.makeSureOrderItemReceived(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(WaitSendProductOrderInformationActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel bean) {
                        Utils.showToast(mContext, bean.message);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }


    /**
     * 确认收货
     */
    public void onConfirmReceiveClick(View view) {
        final ConfirmReceiveDialog dialog = new ConfirmReceiveDialog(mContext);
        dialog.setContentView(null);
        dialog.show();
        dialog.setOnClickListener(new ConfirmReceiveDialog.OnCliclListener() {
            @Override
            public void confirm() {

                AllOrderBusFlag allOrderBusFlg = new AllOrderBusFlag();
                allOrderBusFlg.id = orderId;
                allOrderBusFlg.flagId = 1;
                RxCommonBus.get().post(allOrderBusFlg);
//                 confirmOrder();
            }

            @Override
            public void cancel() {

            }
        });
    }

    private void confirmOrder() {
        Map map = new HashMap();
        map.put("orderId", orderId);
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.makeSureOrderReceived(jsonObject)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(WaitSendProductOrderInformationActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel bean) {
                        Utils.showToast(mContext, bean.message);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 催单
     */
    public void onReminderClick(View view) {
        final UrgeBillDialog dialog = new UrgeBillDialog(mContext);
        dialog.setContentView(null);
        dialog.show();
    }

    /**
     * 退款
     */
    public void onRefundClick(View view) {

    }
}
