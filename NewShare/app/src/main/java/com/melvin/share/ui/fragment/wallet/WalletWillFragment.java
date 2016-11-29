package com.melvin.share.ui.fragment.wallet;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.adapter.WalletMoneyAdapter;
import com.melvin.share.databinding.FragmentWalletWillBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/11/29
 * <p/>
 * 描述：钱包即将可用
 */
public class WalletWillFragment extends BaseFragment implements MyRecyclerView.LoadingListener {

    private FragmentWalletWillBinding binding;
    private Context mContext;
    private RecyclerView recyclerView;
    private WalletMoneyAdapter adpter;
    private List<BaseModel> dataList = new ArrayList<>();
    private View root;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet_will, container, false);
        if (root == null) {
            mContext = getActivity();
            initData();
            initAdapter();
            root = binding.getRoot();
            requestData();
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        recyclerView = binding.recyclerView;
//        recyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
//        recyclerView.setLoadingListener(this);
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        adpter = new WalletMoneyAdapter(mContext, dataList);
        recyclerView.setAdapter(adpter);
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
        dataList.addAll(list);
        adpter.notifyDataSetChanged();

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        dataList.clear();
        requestData();
//        recyclerView.refreshComplete();

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        requestData();
//        recyclerView.loadMoreComplete();
    }
}

