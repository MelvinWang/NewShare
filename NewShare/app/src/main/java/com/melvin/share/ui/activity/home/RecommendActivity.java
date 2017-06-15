package com.melvin.share.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.allure.lbanners.LMBanners;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.HomeShopAdapter;
import com.melvin.share.adapter.RecommendCategoryAdapter;
import com.melvin.share.adapter.UrlImgAdapter;
import com.melvin.share.databinding.ActivityRecommendBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.CategoryBean;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.SearchActivity;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Author: Melvin
 * <p>
 * Data： 2017/3/31
 * <p>
 * 描述：推荐
 */
public class RecommendActivity extends BaseActivity implements MyRecyclerView.LoadingListener {
    private ActivityRecommendBinding binding;
    private Context mContext;
    private List<String> networkImages = new ArrayList<>();

    private HomeShopAdapter shopAdapter;
    private RecommendCategoryAdapter recommendCategoryAdapter;

    private List<BaseModel> data1 = new ArrayList<>();//后面的Banner
    private List<BaseModel> data2 = new ArrayList<>();//推荐分类
    private List<BaseModel> data3 = new ArrayList<>();//推荐店铺

    private MyRecyclerView categoryShopRecyclerView;
    private RecyclerView shopRecyclerView;

    private LMBanners mLBanners;
    private View headerView;
    private View bottomView;
    private int pageNo = 1;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend);
        mContext = this;
        initWindow();
        initData();
        initAdapter();
        requestData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        categoryShopRecyclerView = binding.categoryShopRecyclerView;

        //头部
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = layoutInflater.inflate(R.layout.category_title, null, false);
        mLBanners = (LMBanners) headerView.findViewById(R.id.banners);
        ImageView backImg = (ImageView) headerView.findViewById(R.id.back);
        RelativeLayout searchEnterBtn = (RelativeLayout) headerView.findViewById(R.id.search_enter);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SearchActivity.class));
            }
        });
        //底部店铺
        LayoutInflater bottomLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bottomView = bottomLayoutInflater.inflate(R.layout.category_bottom, null, false);
        shopRecyclerView = (RecyclerView) bottomView.findViewById(R.id.shop_recyclerView);

        categoryShopRecyclerView.addHeaderView(headerView);
//        categoryShopRecyclerView.addFootView(bottomView);

        categoryShopRecyclerView.setLoadingListener(this);
    }


    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryShopRecyclerView.setLayoutManager(linearLayoutManager);
        recommendCategoryAdapter = new RecommendCategoryAdapter(mContext, data2);
        categoryShopRecyclerView.setAdapter(recommendCategoryAdapter);

        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(mContext, 5);
        gridLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        shopRecyclerView.setLayoutManager(gridLayoutManager3);
        shopAdapter = new HomeShopAdapter(mContext, data3);
        shopRecyclerView.setAdapter(shopAdapter);
    }

    /**
     * 请求网络
     */
    private void requestData() {
        requestAD();
        requesRecommentCategory();
        requesRecommentShop();

    }


    /**
     * 轮播图
     */
    private void requestAD() {
        String url = "http://h.hiphotos.baidu.com/image/h%3D300/sign=ff62800b073b5bb5a1d726fe06d2d523/a6efce1b9d16fdfa7807474eb08f8c5494ee7b23.jpg";
        networkImages.clear();
        networkImages.add(url);
        networkImages.add(url);
        networkImages.add(url);
        networkImages.add(url);
        mLBanners.setAdapter(new UrlImgAdapter(mContext), networkImages);
    }

    /**
     * 推荐分类
     */
    private void requesRecommentCategory() {
        Map categoryMap = new HashMap();
        categoryMap.put("pageNo", pageNo);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(categoryMap)));
        fromNetwork.findMainPageProduct(jsonObject)
                .compose(new RxActivityHelper<CommonList<CategoryBean>>().ioMain(RecommendActivity.this, true))
                .subscribe(new RxModelSubscribe<CommonList<CategoryBean>>(mContext, true) {
                    @Override
                    protected void myNext(CommonList<CategoryBean> bean) {
                        data2.addAll(bean.rows);
                        recommendCategoryAdapter.notifyDataSetChanged();
                        if (pageNo == 1) {
                            categoryShopRecyclerView.refreshComplete();
                        } else {
                            categoryShopRecyclerView.loadMoreComplete();
                        }
                    }

                    @Override
                    protected void myError(String message) {
                        categoryShopRecyclerView.refreshComplete();
                        categoryShopRecyclerView.loadMoreComplete();
                        Utils.showToast(mContext, message);
                    }
                });
    }


    /**
     * 推荐店铺
     */
    private void requesRecommentShop() {
        fromNetwork.findRecommendedSeller()
                .compose(new RxActivityHelper<ArrayList<ShopBean>>().ioMain(RecommendActivity.this, true))
                .subscribe(new RxModelSubscribe<ArrayList<ShopBean>>(mContext, true) {
                    @Override
                    protected void myNext(ArrayList<ShopBean> list) {
                        data3.addAll(list);
                        shopAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    @Override
    public void onRefresh() {
        pageNo = 1;
        data2.clear();
        requesRecommentCategory();
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        requesRecommentCategory();
    }


    @Override
    public void onPause() {
        super.onPause();
        mLBanners.stopImageTimerTask();
    }

    @Override
    public void onResume() {
        super.onResume();
        mLBanners.startImageTimerTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLBanners.clearImageTimerTask();
    }


}
