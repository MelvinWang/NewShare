package com.melvin.share.ui.fragment.productinfo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.ProductEvaluateAdapter;
import com.melvin.share.databinding.FragmentProductEvaluateBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/25
 * <p/>
 * 描述：单个商品信息的评价
 */
public class ProductEvaluateFragment extends BaseFragment implements MyRecyclerView.LoadingListener {

    private FragmentProductEvaluateBinding binding;
    private Context mContext;
    private MyRecyclerView mRecyclerView;
    private ProductEvaluateAdapter productEvaluateAdapter;
    private List<BaseModel> data = new ArrayList<>();
    private View root;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_evaluate, container, false);
        if (root == null) {
            mContext = getActivity();
            initData();
            initAdapter();
            root = binding.getRoot();
            requestData();
        } else {
            ViewUtils.removeParent(root);// 移除frameLayout之前的爹
        }
        return root;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        productEvaluateAdapter = new ProductEvaluateAdapter(mContext, data);
        mRecyclerView.setAdapter(productEvaluateAdapter);
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
        productEvaluateAdapter.notifyDataSetChanged();

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        data.clear();
        requestData();
        mRecyclerView.refreshComplete();

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