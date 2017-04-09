package com.melvin.share.modelview.item;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.ImgUrlBean;
import com.melvin.share.network.GlobalUrl;

/**
 * Created Time: 2016/7/23.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：单个商品item的ViewModel
 */
public class ProductInfoimgItemViewModel extends BaseObservable {

    private String url;
    private Context context;

    public ProductInfoimgItemViewModel(Context context, String url) {
        this.url = url;
        this.context = context;
    }

    public void onItemClick(View view) {

    }

    public String getImgUrl() {
        return GlobalUrl.SERVICE_URL + (this.url);
    }

    public void setEntity(String url) {
        this.url = url;
        notifyChange();
    }
}
