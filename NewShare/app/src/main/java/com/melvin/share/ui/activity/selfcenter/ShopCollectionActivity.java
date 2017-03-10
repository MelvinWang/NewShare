package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.databinding.ActivityShopCollectionBinding;
import com.melvin.share.modelview.acti.ShopCollectionViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/3
 * <p/>
 * 描述： 店铺收藏页面
 */
public class ShopCollectionActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityShopCollectionBinding binding;
    private Context mContext = null;
    private MyRecyclerView mRecyclerView;
    private ShopCollectionViewModel shopCollectionViewModel;
    private Map map;
    private int pageNo = 1;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_collection);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }

    private void ininData() {
        map = new HashMap();
        map.put("pageNo", pageNo + "");
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(this);
        shopCollectionViewModel = new ShopCollectionViewModel(this, mRecyclerView, binding.edit, binding.cancel, binding.delete);
        binding.setViewModel(shopCollectionViewModel);
        shopCollectionViewModel.requestData(map);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        pageNo = 1;
        map.put("pageNo", pageNo + "");
        shopCollectionViewModel.requestData(map);
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        pageNo++;
        map.put("pageNo", pageNo + "");
        shopCollectionViewModel.requestData(map);
    }


}
