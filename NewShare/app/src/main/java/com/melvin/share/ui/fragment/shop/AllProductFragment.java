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
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.AllProductAdapter;
import com.melvin.share.databinding.FragmentAllProductBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.ShopInformationActivity;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.view.NoRefreshRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        allProductAdapter = new AllProductAdapter(mContext, data);
        mRecyclerView.setAdapter(allProductAdapter);
    }

    /**
     * 请求网络
     */
    private void requestData() {
        map = new HashMap();
        map.put("seller.id", ShopInformationActivity.sellerId);


        fromNetwork.findProductsBySeller(map)
                .compose(new RxFragmentHelper<ArrayList<Product>>().ioMain(mContext, AllProductFragment.this, true))
                .subscribe(new RxModelSubscribe<ArrayList<Product>>(mContext, true) {
                    @Override
                    protected void myNext(ArrayList<Product> products) {
                        data.addAll(products);
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