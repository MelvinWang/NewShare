package com.melvin.share.ui.activity.shopcar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.databinding.ActivityShoppingCarEditBinding;
import com.melvin.share.modelview.acti.ShopCarEditViewModel;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/3/31
 * <p>
 * 描述：购物车编辑
 */
public class ShoppingCarEditActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityShoppingCarEditBinding binding;
    private Context mContext;
    private MyRecyclerView mRecyclerView;
    private ShopCarEditViewModel shopCarEditViewModel;
    private Map map;
    private int pageNo = 1;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping_car_edit);
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
        shopCarEditViewModel = new ShopCarEditViewModel(this, mRecyclerView);
        binding.setViewModel(shopCarEditViewModel);
        shopCarEditViewModel.requestData(map);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        pageNo = 1;
        map.put("pageNo", pageNo + "");
        shopCarEditViewModel.requestData(map);

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        pageNo++;
        map.put("pageNo", pageNo + "");
        shopCarEditViewModel.requestQueryData(map);
    }


}
