package com.melvin.share.ui.fragment.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.RecommendShopAdapter;
import com.melvin.share.databinding.FragmentHomeBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.ui.activity.home.LocationModeSourceActivity;
import com.melvin.share.ui.activity.home.QRcodeActivity;
import com.melvin.share.view.FullyGridLayoutManager;
import com.melvin.share.view.RxSubscribe;
import com.melvin.share.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/11/29
 * <p>
 * 描述：首页
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private Context mContext;

    private RecommendShopAdapter adpter;
    private List<BaseModel> dataList = new ArrayList<>();

    private RecyclerView recyclerView;
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
        recyclerView = binding.recyclerView;
    }


    /**
     * 初始化点击事件
     */
    private void initClick() {
        binding.scan.setOnClickListener(this);
        binding.sharecode.setOnClickListener(this);
        binding.location.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan:
                Intent openCameraIntent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            case R.id.sharecode:
                startActivity(new Intent(mContext, QRcodeActivity.class));
                break;
            case R.id.location:
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adpter = new RecommendShopAdapter(mContext, dataList);
        recyclerView.setAdapter(adpter);
    }

    /**
     * 请求网络，分享热度商品
     */
    private void requestData() {
        for (int i = 0; i <= 10; i++) {
            User user = new User();
            user.username = "username" + i;
            dataList.add(user);
        }
        adpter.notifyDataSetChanged();
//        fromNetwork.findRecommendedSeller()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new RxSubscribe<ArrayList<ShopBean>>(mContext) {
//                    @Override
//                    protected void myNext(ArrayList<ShopBean> list) {
//                        dataList.addAll(list);
//                        adpter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    protected void myError(String message) {
//
//                    }
//
//                });
    }
}