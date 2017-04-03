package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.model.CategoryBean;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.ProductInfoActivity;

/**
 * Created Time: 2017/3/31.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：推荐的分类商品item的ViewModel
 */
public class CategoryItemViewModel extends BaseObservable {

    private  CategoryBean.MainProductsBean bean;
    private Context context;

    public CategoryItemViewModel(Context context, CategoryBean.MainProductsBean bean) {
        this.bean = bean;
        this.context = context;
    }
    public void onItemClick(View view) {
//        Intent intent = new Intent(context, ProductInfoActivity.class);
//        intent.putExtra("productId",product.id);
//        context.startActivity(intent);
    }

    public String getImgUrl() {
        String[] split = bean.mainPicture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            return url;
        }
        return "";
    }

    public String getShareTimes() {
        return bean.shareTimes;
    }

    public String getName() {
        return bean.name;
    }

    public void setEntity( CategoryBean.MainProductsBean bean) {
        this.bean = bean;
        notifyChange();
    }
}
