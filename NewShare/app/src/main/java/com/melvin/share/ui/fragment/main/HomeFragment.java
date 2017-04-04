package com.melvin.share.ui.fragment.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.R;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.ShareHotAdapter;
import com.melvin.share.databinding.FragmentHomeBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Reward;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.home.LocationModeSourceActivity;
import com.melvin.share.ui.activity.home.RecommendActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.zbar.ZbarCaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ShareHotAdapter adpter;
    private List<BaseModel> dataList = new ArrayList<>();
    private MyRecyclerView recyclerView;
    private View root;
    private View headerView;
    private ImageView scanButton;
    private ImageView recommendButton;
    private ImageView locationButton;
    private Map map;
    private int pageNo = 1;
    private TextView cashbackUse;
    private TextView cashbackWill;

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
        map = new HashMap();
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

        cashbackUse = (TextView) headerView.findViewById(R.id.cashback_use);
        cashbackWill = (TextView) headerView.findViewById(R.id.cashback_will);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(mContext, ZbarCaptureActivity.class);
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
        adpter = new ShareHotAdapter(mContext, dataList);
        recyclerView.setAdapter(adpter);
    }


    private void requestData() {
        requestHotProduct();
        requestReward();

    }


    /**
     * 请求网络，分享热度商品
     */
    private void requestHotProduct() {
        map.put("pageNo", pageNo);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findHotProduct(jsonObject)
                .compose(new RxFragmentHelper<CommonList<HomeHotProduct>>().ioMain(mContext, HomeFragment.this, true))
                .subscribe(new RxModelSubscribe<CommonList<HomeHotProduct>>(mContext, true) {
                    @Override
                    protected void myNext(CommonList<HomeHotProduct> commonList) {
                        dataList.addAll(commonList.rows);
                        adpter.notifyDataSetChanged();
                        if (pageNo == 1) {
                            recyclerView.refreshComplete();
                        } else {
                            recyclerView.loadMoreComplete();
                        }

                    }


                    @Override
                    protected void myError(String message) {
                        recyclerView.refreshComplete();
                        recyclerView.loadMoreComplete();
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 请求返利
     */
    private void requestReward() {
        fromNetwork.findMyTotalReward(ShapreUtils.getCustomerId())
                .compose(new RxFragmentHelper<Reward>().ioMain(mContext, HomeFragment.this, false))
                .subscribe(new RxModelSubscribe<Reward>(mContext, false) {
                    @Override
                    protected void myNext(Reward bean) {
                        cashbackUse.setText(bean.cashbackUse);
                        cashbackWill.setText(bean.cashbackWill);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        pageNo = 1;
        dataList.clear();
        requestHotProduct();


    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
        pageNo++;
        requestHotProduct();

    }
}