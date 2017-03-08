package com.melvin.share.ui.fragment.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.RecommendShopAdapter;
import com.melvin.share.databinding.FragmentHomeBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.home.LocationModeSourceActivity;
import com.melvin.share.ui.activity.home.RecommendActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/11/29
 * <p>
 * 描述：首页
 */
public class HomeFragment extends BaseFragment implements MyRecyclerView.LoadingListener {

    private FragmentHomeBinding binding;
    private Context mContext;
    private RecommendShopAdapter adpter;
    private List<BaseModel> dataList = new ArrayList<>();
    private MyRecyclerView recyclerView;
    private View root;
    private View headerView;
    private ImageView scanButton;
    private ImageView recommendButton;
    private ImageView locationButton;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
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
        recyclerView = binding.recyclerView;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        //头部
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = layoutInflater.inflate(R.layout.home_title, null, false);
        scanButton = (ImageView) headerView.findViewById(R.id.scan);
        recommendButton = (ImageView) headerView.findViewById(R.id.recommend);
        locationButton = (ImageView) headerView.findViewById(R.id.location);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });
        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RecommendActivity.class));
            }
        });
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LocationModeSourceActivity.class));
            }
        });

        recyclerView.addHeaderView(headerView);
        recyclerView.setLoadingListener(this);
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

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        dataList.clear();
        requestData();
        recyclerView.refreshComplete();

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        requestData();
        recyclerView.loadMoreComplete();
    }
}