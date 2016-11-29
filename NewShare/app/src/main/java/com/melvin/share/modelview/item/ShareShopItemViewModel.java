package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.ShopInformationActivity;

/**
 * Created Time: 2016/7/31.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：分享热度item的ViewModel
 */
public class ShareShopItemViewModel extends BaseObservable {

    private ShopBean shopBean;
    private Context context;


    public ShareShopItemViewModel(Context context, ShopBean shopBean) {
        this.shopBean = shopBean;
        this.context = context;
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(context, ShopInformationActivity.class);
        intent.putExtra("shopBean",shopBean);
        context.startActivity(intent);

    }
    public String getShopName() {
        return shopBean.name;
    }
    public String getImgUrl() {
        String[] split = shopBean.logo.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            LogUtils.e("哈哈"+url);
            return url;
        }
        return "";
    }

    public void setEntity(ShopBean shopBean) {
        this.shopBean = shopBean;
        notifyChange();
    }
}
