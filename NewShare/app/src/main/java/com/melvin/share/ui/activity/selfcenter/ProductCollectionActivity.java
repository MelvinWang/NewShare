package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.databinding.ActivityProductCollectionBinding;
import com.melvin.share.modelview.acti.ProductCollectionViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/4
 * <p>
 * 描述： 商品收藏页面
 */
public class ProductCollectionActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityProductCollectionBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private LinearLayout mRoot;
    private ProductCollectionViewModel productCollectionViewModel;
    private Map map;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_collection);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        map=new HashMap();
        map.put("customer.id", "1");
        mRoot = binding.root;
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        productCollectionViewModel = new ProductCollectionViewModel(this, mRecyclerView, mRoot, binding.edit, binding.cancel, binding.delete);
        binding.setViewModel(productCollectionViewModel);
        productCollectionViewModel.requestData(map);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        productCollectionViewModel.requestData(map);
        mRecyclerView.refreshComplete();
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {

    }


}
