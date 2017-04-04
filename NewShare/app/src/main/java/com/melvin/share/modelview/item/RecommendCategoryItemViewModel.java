package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.melvin.share.adapter.CategoryAdapter;
import com.melvin.share.adapter.HotLabelAdapter;
import com.melvin.share.model.CategoryBean;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.ShopInformationActivity;
import com.melvin.share.ui.activity.order.CategoryActivity;

/**
 * Created Time: 2017/3/31.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：推荐分类商品item的ViewModel
 */
public class RecommendCategoryItemViewModel extends BaseObservable {

    private CategoryBean bean;
    private Context context;
    private RecyclerView recyclerView;

    public RecommendCategoryItemViewModel(Context context, CategoryBean bean, RecyclerView recyclerView) {
        this.bean = bean;
        this.context = context;
        this.recyclerView = recyclerView;
        setRecyclerData();
    }
    private void setRecyclerData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        CategoryAdapter adpter = new CategoryAdapter(context, bean.mainProducts);
        recyclerView.setAdapter(adpter);
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra("categoryId", bean.categoryId);
        intent.putExtra("categoryName", bean.categoryName);
        context.startActivity(intent);

    }

    public String getShopName() {
        return bean.categoryName;
    }

    public String getImgUrl() {
        String[] split = bean.categoryPicture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            return url;
        }
        return "";
    }

    public void setEntity(CategoryBean bean) {
        this.bean = bean;
        setRecyclerData();
        notifyChange();
    }
}
