package com.melvin.share.ui.activity.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.allure.lbanners.LMBanners;
import com.melvin.share.R;
import com.melvin.share.Utils.DateUtil;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.HomeProductAdapter;
import com.melvin.share.adapter.HomeShopAdapter;
import com.melvin.share.adapter.UrlImgAdapter;
import com.melvin.share.databinding.ActivityRecommendBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Category;
import com.melvin.share.model.Product;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.SearchActivity;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.NoScrollRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/3/7
 * <p>
 * 描述：首页推荐
 */
public class RecommendActivity extends BaseActivity  implements View.OnClickListener{
    private ActivityRecommendBinding binding;
    private Context mContext;
    private LMBanners mLBanners;
    private List<String> networkImages = new ArrayList<>();
    private HomeProductAdapter newProductAdapter;
    private HomeProductAdapter userRecommendProductAdapter;
    private HomeShopAdapter shopAdapter;

    private List<BaseModel> data1 = new ArrayList<>();
    private List<BaseModel> data2 = new ArrayList<>();
    private List<BaseModel> data3 = new ArrayList<>();
    private List<Category> categoryList = new ArrayList<>();
    private NoScrollRecyclerView newRecyclerView;
    private NoScrollRecyclerView userRecommendRecyclerView;
    private NoScrollRecyclerView shopRecyclerView;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend);
        mContext = this;
        initWindow();
        initData();
    }
    /**
     * 初始化数据
     */
    private void initData() {
        mLBanners = binding.banners;
        newRecyclerView = binding.newRecyclerView;
        userRecommendRecyclerView = binding.userRecommendRecyclerView;
        shopRecyclerView = binding.shopRecyclerView;
        initAdapter();
        initClick();
        requestData();
    }


    /**
     * 初始化点击事件
     */
    private void initClick() {
        binding.searchEnter.setOnClickListener(this);
        //首页8个主题
        binding.homeDelicious.setOnClickListener(this);
        binding.homeCloth.setOnClickListener(this);
        binding.homeEle.setOnClickListener(this);
        binding.homeMarkup.setOnClickListener(this);
        binding.homeFurnish.setOnClickListener(this);
        binding.homeOrnament.setOnClickListener(this);
        binding.homeShoes.setOnClickListener(this);
        binding.homeOther.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {

            case R.id.search_enter:
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
            //首页8个主题
            case R.id.home_delicious:
                intent.setClass(mContext, DeliciousActivity.class);
                intent.putExtra("id", "1");
                startActivity(intent);
                break;
            case R.id.home_cloth:
                intent.setClass(mContext, ClothActivity.class);
                intent.putExtra("id", "2");
                startActivity(intent);
                break;
            case R.id.home_ele:
                intent.setClass(mContext, DigitalActivity.class);
                intent.putExtra("id", "3");
                startActivity(intent);
                break;
            case R.id.home_markup:
                intent.setClass(mContext, MarkUpActivity.class);
                intent.putExtra("id", "4");
                startActivity(intent);
                break;
            case R.id.home_furnish:
                intent.setClass(mContext, FurnitureActivity.class);
                intent.putExtra("id", "5");
                startActivity(intent);
                break;
            case R.id.home_ornament:
                intent.setClass(mContext, OrnamentActivity.class);
                intent.putExtra("id", "6");
                startActivity(intent);
                break;
            case R.id.home_shoes:
                intent.setClass(mContext, ShoesActivity.class);
                intent.putExtra("id", "7");
                startActivity(intent);
                break;
            case R.id.home_other:
                Utils.showToast(mContext, "其他页面");
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
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mContext, 4);
        gridLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        newRecyclerView.setLayoutManager(gridLayoutManager1);
        newProductAdapter = new HomeProductAdapter(mContext, data1);
        newRecyclerView.setAdapter(newProductAdapter);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(mContext, 4);
        gridLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        userRecommendRecyclerView.setLayoutManager(gridLayoutManager2);
        userRecommendProductAdapter = new HomeProductAdapter(mContext, data2);
        userRecommendRecyclerView.setAdapter(userRecommendProductAdapter);

        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(mContext, 4);
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
        requestCategory();
        requestNewProduct();
        requesRecommentShop();

    }
    /**
     * 轮播图
     */
    private void requestAD() {
        addUrilImg();
        mLBanners.setAdapter(new UrlImgAdapter(mContext), networkImages);
    }

    /**
     * 商品分类
     */
    private void requestCategory() {

        fromNetwork.categoryFind()
                .compose(new RxActivityHelper<ArrayList<Category>>().ioMain(RecommendActivity.this,true))
                .subscribe(new RxModelSubscribe<ArrayList<Category>>(mContext, true) {
                    @Override
                    protected void myNext(ArrayList<Category> categorys) {
                        LogUtils.i("商品分类");
                        categoryList.addAll(categorys);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 新商品
     */
    private void requestNewProduct() {
        Map map = new HashMap();
        map.put("createTime", DateUtil.getNowPlusTime());
        map.put("rows", "4");

        fromNetwork.findProductsInHomePage(map)
                .compose(new RxActivityHelper<ArrayList<Product>>().ioMain(RecommendActivity.this,true))
                .subscribe(new RxModelSubscribe<ArrayList<Product>>(mContext, true) {
                    @Override
                    protected void myNext(ArrayList<Product> productList) {
                        LogUtils.i("新商品");
                        data1.addAll(productList);
                        newProductAdapter.notifyDataSetChanged();
                        requestRecommendProdcut();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 推荐商品
     */
    private void requestRecommendProdcut() {
        Map map = new HashMap();
        map.put("saleTotal", "1");
        map.put("rows", "4");

        fromNetwork.findProductsInHomePage(map)
                .compose(new RxActivityHelper<ArrayList<Product>>().ioMain(RecommendActivity.this,true))
                .subscribe(new RxModelSubscribe<ArrayList<Product>>(mContext, true) {
                    @Override
                    protected void myNext(ArrayList<Product> productList) {
                        LogUtils.i("推荐商品");
                        data2.addAll(productList);
                        userRecommendProductAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 推荐店铺
     */
    private void requesRecommentShop() {

        fromNetwork.findRecommendedSeller()
                .compose(new RxActivityHelper<ArrayList<ShopBean>>().ioMain(RecommendActivity.this,true))
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

    private void addUrilImg() {
        networkImages.clear();
        networkImages.add("http://h.hiphotos.baidu.com/image/h%3D300/sign=ff62800b073b5bb5a1d726fe06d2d523/a6efce1b9d16fdfa7807474eb08f8c5494ee7b23.jpg");
        networkImages.add("http://g.hiphotos.baidu.com/image/h%3D300/sign=0a9ac84f89b1cb1321693a13ed5556da/1ad5ad6eddc451dabff9af4bb2fd5266d0163206.jpg");
        networkImages.add("http://a.hiphotos.baidu.com/image/h%3D300/sign=61660ec2207f9e2f6f351b082f31e962/500fd9f9d72a6059e5c05d3e2f34349b023bbac6.jpg");
        networkImages.add("http://c.hiphotos.baidu.com/image/h%3D300/sign=f840688728738bd4db21b431918a876c/f7246b600c338744c90c3826570fd9f9d62aa09a.jpg");

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
