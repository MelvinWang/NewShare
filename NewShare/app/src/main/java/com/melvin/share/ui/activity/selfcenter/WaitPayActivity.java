package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.databinding.ActivityWaitPayBinding;
import com.melvin.share.modelview.acti.WaitPayViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/4
 * <p/>
 * 描述： 待付款页面
 */
public class WaitPayActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityWaitPayBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private WaitPayViewModel waitPayViewModel;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wait_pay);
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
        waitPayViewModel = new WaitPayViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(waitPayViewModel);
        waitPayViewModel.requestData();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        waitPayViewModel.requestData();
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }


}
