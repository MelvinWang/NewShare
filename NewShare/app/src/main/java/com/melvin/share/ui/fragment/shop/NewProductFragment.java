package com.melvin.share.ui.fragment.shop;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.DateUtil;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.NewProductAdapter;
import com.melvin.share.databinding.FragmentNewProductBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.ShopInformationActivity;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.view.NoRefreshRecyclerView;
import com.melvin.share.view.RxSubscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/25
 * <p/>
 * 描述：新品上架
 */
public class NewProductFragment extends BaseFragment implements NoRefreshRecyclerView.LoadingListener {

    private FragmentNewProductBinding binding;
    private Context mContext;
    private NoRefreshRecyclerView mRecyclerView;
    private NewProductAdapter newProductAdapter;
    private List<BaseModel> data = new ArrayList<>();
    private View root;
    private Map map;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_prodcut, container, false);
        if (root == null) {
            mContext = getActivity();
            map = new HashMap();
            initData();
            initAdapter();
            requestData();
            root = binding.getRoot();
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
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLoadingListener(this);
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        newProductAdapter = new NewProductAdapter(mContext, data);
        mRecyclerView.setAdapter(newProductAdapter);
    }

    /**
     * 请求网络
     */
    private void requestData() {
        map.put("seller.id", ShopInformationActivity.sellerId);
        map.put("createTime", DateUtil.getNowPlusTime());
        fromNetwork.findProductsBySeller(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<ArrayList<Product>>(mContext) {
                    @Override
                    protected void myNext(ArrayList<Product> list) {
                        data.addAll(list);
                        newProductAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });

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