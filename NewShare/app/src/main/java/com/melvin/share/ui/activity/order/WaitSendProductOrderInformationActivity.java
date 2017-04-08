package com.melvin.share.ui.activity.order;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.DateUtil;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityWaitSendProdOrderInfoBinding;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.modelview.acti.WaitSendProOrderInfoViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.dialog.UrgeBillDialog;

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

        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        orderId = getIntent().getStringExtra("orderId");
        LogUtils.i("orderId"+orderId);
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
     * 确认收货
     */
    public void onConfirmReceiveClick(View view) {
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
