package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.databinding.ActivityRefundBinding;
import com.melvin.share.modelview.acti.RefundViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/6
 * <p/>
 * 描述： 退款页面
 */
public class RefundActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityRefundBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private RefundViewModel refundViewModel;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refund);
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
        refundViewModel = new RefundViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(refundViewModel);
        refundViewModel.requestData();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        refundViewModel.requestData();
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }


}
