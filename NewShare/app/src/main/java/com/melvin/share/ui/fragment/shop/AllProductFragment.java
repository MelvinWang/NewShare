package com.melvin.share.ui.fragment.shop;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.AllProductAdapter;
import com.melvin.share.databinding.FragmentAllProductBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.BaseReturnModel;
import com.melvin.share.ui.activity.ShopInformationActivity;
import com.melvin.share.ui.activity.selfcenter.ManageAddressActivity;
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
 * <p>
 * Data： 2016/7/25
 * <p>
 * 描述：全部商品
 */
public class AllProductFragment extends BaseFragment implements NoRefreshRecyclerView.LoadingListener {

    private FragmentAllProductBinding binding;
    private Context mContext;
    private NoRefreshRecyclerView mRecyclerView;
    private AllProductAdapter allProductAdapter;
    private List<BaseModel> data = new ArrayList<>();
    private View root;
    private Map map;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_product, container, false);
        if (root == null) {
            mContext = getActivity();
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
        GridLayoutManager gridLayoutManager =  new GridLayoutManager(mContext,2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        allProductAdapter = new AllProductAdapter(mContext, data);
        mRecyclerView.setAdapter(allProductAdapter);
    }

    /**
     * 请求网络
     */
    private void requestData() {
        map=new HashMap();
        map.put("seller.id", ShopInformationActivity.sellerId);
        fromNetwork.findProductsBySeller(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<ArrayList<Product>>(mContext) {
                    @Override
                    protected void myNext(ArrayList<Product> list) {
                        data.addAll(list);
                        allProductAdapter.notifyDataSetChanged();
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