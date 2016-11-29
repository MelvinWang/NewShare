package com.melvin.share.modelview.item;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.ImgUrlBean;

/**
 * Created Time: 2016/7/23.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：单个商品item的ViewModel
 */
public class ProductInfoimgItemViewModel extends BaseObservable {

    private ImgUrlBean imgUrlBean;
    private Context context;

    public ProductInfoimgItemViewModel(Context context,ImgUrlBean imgUrlBean) {
        this.imgUrlBean = imgUrlBean;
        this.context = context;
    }

    public void onItemClick(View view) {

    }

    public String getImgUrl() {

        return imgUrlBean.url;
    }

    public void setEntity(ImgUrlBean imgUrlBean) {
        this.imgUrlBean = imgUrlBean;
        notifyChange();
    }
}
