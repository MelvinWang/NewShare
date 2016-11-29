package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.Utils.RxBus;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.ShopInformationActivity;

/**
 * Created Time: 2016/7/23.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：店铺码item的ViewModel
 */
public class ShopCodeItemViewModel extends BaseObservable {

    private User user;
    private Context context;

    public ShopCodeItemViewModel(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public void onItemClick(View view) {
        context.startActivity(new Intent(context, ShopInformationActivity.class));
    }
    public void onclickShare(View view) {
        RxBus.get().post("hello22");
    }
    public String getImgUrl() {
        return "http://h.hiphotos.baidu.com/image/h%3D300/sign=ff62800b073b5bb5a1d726fe06d2d523/a6efce1b9d16fdfa7807474eb08f8c5494ee7b23.jpg";
    }

    public void setEntity(User user) {
        this.user = user;
        notifyChange();
    }
}
