package com.melvin.share.ui.fragment.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.HomeProductAdapter;
import com.melvin.share.adapter.ShareShopAdapter;
import com.melvin.share.databinding.FragmentHomeBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Category;
import com.melvin.share.model.Product;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.ui.activity.SearchActivity;
import com.melvin.share.ui.activity.home.LocationModeSourceActivity;
import com.melvin.share.view.NoScrollRecyclerView;
import com.melvin.share.view.RxListSubscribe;
import com.melvin.share.view.RxSubscribe;
import com.melvin.share.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/10/1
 * <p/>
 * 描述：首页
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private Context mContext;

    private ShareShopAdapter shopAdapter;
    private List<BaseModel> data3 = new ArrayList<>();

    private NoScrollRecyclerView shopRecyclerView;
    private View root;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        if (root == null) {
            mContext = getActivity();
            initClick();
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
        shopRecyclerView = binding.shopRecyclerView;

    }


    /**
     * 初始化点击事件
     */
    private void initClick() {
        binding.homeScan.setOnClickListener(this);
        binding.homeLocation.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.home_scan:
                Intent openCameraIntent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            case R.id.home_location:
                startActivity(new Intent(mContext, LocationModeSourceActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ((Activity) mContext).RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Utils.showToast(mContext, scanResult);
        }
    }


    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(mContext, 4);
        gridLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        shopRecyclerView.setLayoutManager(gridLayoutManager3);
        shopAdapter = new ShareShopAdapter(mContext, data3);
        shopRecyclerView.setAdapter(shopAdapter);
    }

    /**
     * 请求网络
     */
    private void requestData() {
        requesRecommentShop();

    }
    /**
     * 推荐店铺
     */
    private void requesRecommentShop() {
        fromNetwork.findRecommendedSeller()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<ArrayList<ShopBean>>(mContext) {
                    @Override
                    protected void myNext(ArrayList<ShopBean> list) {
                        data3.addAll(list);
                        shopAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void myError(String message) {

                    }

                });
    }
}