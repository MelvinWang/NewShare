package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.databinding.ActivityMessageBinding;
import com.melvin.share.modelview.acti.MessageViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/7
 * <p/>
 * 描述： 消息通知
 */
public class MessageActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityMessageBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private MessageViewModel messageViewModel;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_message);
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
        messageViewModel = new MessageViewModel(this, mRecyclerView, mRoot);
        binding.setViewModel(messageViewModel);
        messageViewModel.requestData();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        messageViewModel.requestData();
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }
}
