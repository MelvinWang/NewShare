package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;
import android.widget.CompoundButton;

import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.ShopInformationActivity;

/**
 * Created Time: 2016/8/3.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：店铺收藏item的ViewModel
 */
public class ShopCollectionItemViewModel extends BaseObservable {

    private ShopBean bean;
    private Context context;
    private String number = "1";

    public ShopCollectionItemViewModel(Context context, ShopBean bean) {
        this.bean = bean;
        this.context = context;
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(context, ShopInformationActivity.class);
        intent.putExtra("shopBean", bean);
        context.startActivity(intent);
    }


    public void setIsShow(boolean b) {
        bean.isShow = b;
    }

    public boolean getIsShow() {
        return bean.isShow;
    }

    public void setIsFocus(boolean b) {
        bean.isChecked = b;
    }

    public boolean getIsFocus() {
        return bean.isChecked;
    }

    public String getName() {
        return bean.name;
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
        String[] split = bean.picture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            return url;
        }
        return "";
    }

    public void setEntity(ShopBean bean) {
        this.bean = bean;
        notifyChange();
    }
}
