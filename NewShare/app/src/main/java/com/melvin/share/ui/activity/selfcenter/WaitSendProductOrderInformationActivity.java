package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.databinding.ActivityWaitSendProdOrderInfoBinding;
import com.melvin.share.modelview.acti.WaitSendProOrderInfoViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.dialog.UrgeBillDialog;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/7
 * <p>
 * 描述： 待发货订单信息
 */
public class WaitSendProductOrderInformationActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityWaitSendProdOrderInfoBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private WaitSendProOrderInfoViewModel waitSendProOrderInfoViewModel;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wait_sendpro_order_info);
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
        waitSendProOrderInfoViewModel = new WaitSendProOrderInfoViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(waitSendProOrderInfoViewModel);
        waitSendProOrderInfoViewModel.requestData();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        waitSendProOrderInfoViewModel.requestData();
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }

    /**
     * 催单
     */
    public void urgeBill(View v) {
        final UrgeBillDialog dialog = new UrgeBillDialog(mContext);
        dialog.setContentView(null);
        dialog.show();
    }


}
