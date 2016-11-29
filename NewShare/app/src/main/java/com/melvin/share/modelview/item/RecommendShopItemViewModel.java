package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.User;
import com.melvin.share.ui.activity.ProductInfoActivity;

/**
 * Created Time: 2016/7/31.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：分享热度item的ViewModel
 */
public class RecommendShopItemViewModel extends BaseObservable {

    private User model;
    private Context context;
    String url = "http://h.hiphotos.baidu.com/image/h%3D300/sign=ff62800b073b5bb5a1d726fe06d2d523/a6efce1b9d16fdfa7807474eb08f8c5494ee7b23.jpg";

    public RecommendShopItemViewModel(Context context, User model) {
        this.model = model;
        this.context = context;
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(context, ProductInfoActivity.class);

        intent.putExtra("productId", "1");
        context.startActivity(intent);

    }

    public String getName() {
        return model.username;
    }
    public String getShareNumber() {
        return model.username;
    }
    public String getImgUrl() {
        return url;
    }

    public void setEntity(User model) {
        this.model = model;
        notifyChange();
    }
}
