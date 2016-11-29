package com.melvin.share.modelview.item;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.User;

/**
 * Created Time: 2016/7/23.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：单个商品信息的评价item的ViewModel
 */
public class ProductEvaluateItemViewModel extends BaseObservable {

    private User user;
    private Context context;

    public ProductEvaluateItemViewModel(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public void onItemClick(View view) {

    }

    public String getImgUrl() {
        return "http://h.hiphotos.baidu.com/image/h%3D300/sign=ff62800b073b5bb5a1d726fe06d2d523/a6efce1b9d16fdfa7807474eb08f8c5494ee7b23.jpg";
    }

    public void setEntity(User user) {
        this.user = user;
        notifyChange();
    }
}
