package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.databinding.ActivityWaitEvaluateBinding;
import com.melvin.share.modelview.acti.WaitEvaluateViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/6
 * <p/>
 * 描述： 待评价页面
 */
public class WaitEvaluateActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityWaitEvaluateBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private WaitEvaluateViewModel waitEvaluateViewModel;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wait_evaluate);
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
        waitEvaluateViewModel = new WaitEvaluateViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(waitEvaluateViewModel);
        waitEvaluateViewModel.requestData();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        waitEvaluateViewModel.requestData();
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }


}
