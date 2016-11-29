package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityWaitPayOrderInfoBinding;
import com.melvin.share.dialog.OrderCancelDialog;
import com.melvin.share.dialog.PaySuccessDialog;
import com.melvin.share.modelview.acti.WaitPayOrderInfoViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/7
 * <p>
 * 描述： 待付款订单信息
 */
public class WaitPayOrderInformationActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityWaitPayOrderInfoBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private WaitPayOrderInfoViewModel waitPayOrderInfoViewModel;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wait_pay_order_info);
        mContext = this;

        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        mRoot = binding.root;
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        waitPayOrderInfoViewModel = new WaitPayOrderInfoViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(waitPayOrderInfoViewModel);
        waitPayOrderInfoViewModel.requestData();
    }

    /**
     * 取消订单
     */
    public void cancelPay(View v) {
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
                Utils.showToast(mContext, "cancel");

            }
        });
    }

    /**
     * 付款
     */
    public void goToPay(View v) {
        final PaySuccessDialog dialog = new PaySuccessDialog(mContext);
        dialog.setContentView(null);
        dialog.show();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        waitPayOrderInfoViewModel.requestData();
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }
}
