package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.databinding.ActivityWaitEvaluateOrderInfoBinding;
import com.melvin.share.modelview.acti.WaitEvaluateOrderInfoViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/7
 * <p/>
 * 描述： 待评价订单信息
 */
public class WaitEvaluateOrderInformationActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityWaitEvaluateOrderInfoBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private WaitEvaluateOrderInfoViewModel waitEvaluateOrderInfoViewModel;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wait_evaluate_order_info);
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
        waitEvaluateOrderInfoViewModel = new WaitEvaluateOrderInfoViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(waitEvaluateOrderInfoViewModel);
        waitEvaluateOrderInfoViewModel.requestData();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        waitEvaluateOrderInfoViewModel.requestData();
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }
}
