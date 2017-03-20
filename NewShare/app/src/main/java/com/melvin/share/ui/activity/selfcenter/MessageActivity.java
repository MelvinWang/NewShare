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

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/3/20
 * <p/>
 * 描述： 消息通知
 */
public class MessageActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityMessageBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private MessageViewModel messageViewModel;
    private Map map;
    private int pageNo = 1;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_message);
        mContext = this;

        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        map=new HashMap();
        map.put("pageNo", pageNo + "");
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        messageViewModel = new MessageViewModel(this, mRecyclerView);
        binding.setViewModel(messageViewModel);
        messageViewModel.requestData(map);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        pageNo = 1;
        map.put("pageNo", pageNo + "");
        messageViewModel.requestData(map);
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        pageNo++;
        map.put("pageNo", pageNo + "");
        messageViewModel.requestQueryData(map);
    }
}
