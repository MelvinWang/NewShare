package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.melvin.share.Utils.RxBus;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.ProductInfoActivity;
import com.melvin.share.ui.activity.common.CommonImageActivity;

/**
 * Created Time: 2016/7/23.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：订单码item的ViewModel
 */
public class OrderCodeItemViewModel extends BaseObservable {
        String url="http://h.hiphotos.baidu.com/image/h%3D300/sign=ff62800b073b5bb5a1d726fe06d2d523/a6efce1b9d16fdfa7807474eb08f8c5494ee7b23.jpg";
    private User user;
    private Context context;

    public OrderCodeItemViewModel(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public void onItemClick(View view) {
        context.startActivity(new Intent(context, ProductInfoActivity.class));
    }

    public void onImageClick(View view) {
        Intent intent=new Intent(context, CommonImageActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        intent.putExtra("imageUrl",url);
        context.startActivity(intent,options.toBundle());
    }
    public void onclickShare(View view) {
        RxBus.get().post("hello11");
    }

    public String getImgUrl() {
        return url;
    }

    public void setEntity(User user) {
        this.user = user;
        notifyChange();
    }
}
