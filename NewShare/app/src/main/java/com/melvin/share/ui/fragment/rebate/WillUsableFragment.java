package com.melvin.share.ui.fragment.rebate;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.adapter.WillUsableAdapter;
import com.melvin.share.databinding.FragmentWillUsableBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.NoRefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/7/21
 * <p>
 * 描述：即将可用
 */
public class WillUsableFragment extends BaseFragment implements NoRefreshRecyclerView.LoadingListener {

    private FragmentWillUsableBinding binding;
    private Context mContext;
    private NoRefreshRecyclerView mRecyclerView;
    private WillUsableAdapter willUsableAdapter;
    private List<BaseModel> data = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_will_usable, container, false);
        mContext = getActivity();
        initData();
        initAdapter();
        return binding.getRoot();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingListener(this);
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        willUsableAdapter = new WillUsableAdapter(mContext, data);
        mRecyclerView.setAdapter(willUsableAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        requestData();
    }

    /**
     * 请求网络
     */
    private void requestData() {
        List list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.password = i + "";
            user.username = i + "";
            list.add(user);
        }
        data.addAll(list);
        willUsableAdapter.notifyDataSetChanged();

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        requestData();
        mRecyclerView.loadMoreComplete();
    }
}