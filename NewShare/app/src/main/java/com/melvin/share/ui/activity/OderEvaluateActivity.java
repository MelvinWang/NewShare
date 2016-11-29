package com.melvin.share.ui.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.databinding.ActivityOrderEvaluateBinding;
import com.melvin.share.modelview.acti.OrderEvaluateViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/4
 * <p/>
 * 描述： 订单评价页面
 */
public class OderEvaluateActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityOrderEvaluateBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private OrderEvaluateViewModel orderEvaluateViewModel;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_evaluate);
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
        orderEvaluateViewModel = new OrderEvaluateViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(orderEvaluateViewModel);
        orderEvaluateViewModel.requestData();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        orderEvaluateViewModel.requestData();
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }
}
