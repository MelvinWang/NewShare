package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;
import android.widget.CompoundButton;

import com.melvin.share.model.User;
import com.melvin.share.ui.activity.ShopInformationActivity;

/**
 * Created Time: 2016/8/3.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：店铺收藏item的ViewModel
 */
public class ShopCollectionItemViewModel extends BaseObservable {

    private User user;
    private Context context;
    private String number = "1";

    public ShopCollectionItemViewModel(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public void onItemClick(View view) {
        context.startActivity(new Intent(context, ShopInformationActivity.class));
    }


    public void setIsShow(boolean b) {
        user.isShow = b;
    }

    public boolean getIsShow() {
        return user.isShow;
    }

    public void setIsFocus(boolean b) {
        user.isChecked = b;
    }

    public boolean getIsFocus() {
        return user.isChecked;
    }

    /**
     * 勾选状态判断,修改值后，以便操作之时ViewModel可以利用到
     *
     * @return
     */
    public CompoundButton.OnCheckedChangeListener getChoiceListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setIsFocus(isChecked);
                notifyChange();
            }
        };
    }

    public String getImgUrl() {
        return "http://h.hiphotos.baidu.com/image/h%3D300/sign=ff62800b073b5bb5a1d726fe06d2d523/a6efce1b9d16fdfa7807474eb08f8c5494ee7b23.jpg";
    }

    public void setEntity(User user) {
        this.user = user;
        notifyChange();
    }
}
