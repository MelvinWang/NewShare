package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;
import android.widget.CompoundButton;

import com.melvin.share.model.User;
import com.melvin.share.ui.activity.ProductInfoActivity;

/**
 * Created Time: 2016/8/7.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：消息通知页面item的ViewModel
 */
public class MessageItemViewModel extends BaseObservable {

    private User user;
    private Context context;

    public MessageItemViewModel(Context context, User user) {
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
