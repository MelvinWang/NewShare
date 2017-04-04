package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.ProductInfoActivity;

/**
 * Created Time: 2017/4/4.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：搜索出商品来的页面item的ViewModel
 */
public class SearchProductItemViewModel extends BaseObservable {

    private Product bean;
    private Context context;

    public SearchProductItemViewModel(Context context, Product bean) {
        this.bean = bean;
        this.context = context;
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(context, ProductInfoActivity.class);
        intent.putExtra("productId",bean.id);
        context.startActivity(intent);
    }

    public String getImgUrl() {
        String[] split = bean.mainPicture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            LogUtils.e("哈哈"+url);
            return url;
        }
        return "";
    }

    public String getShareTimes() {
        return bean.shareTimes;
    }

    public String getProductName() {
        return bean.name;
    }

    public String getPrice() {
        return "￥:"+bean.price;
    }

    public String getPlace() {
        return bean.place;
    }
    public void setEntity(Product bean) {
        this.bean = bean;
        notifyChange();
    }
}
