package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.network.GlobalFlag;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.rx.RxBus;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.ShopInformationActivity;

import static com.melvin.share.ui.activity.ProductInfoActivity.productDetail;

/**
 * Created Time: 2017/4/5.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：店铺码item的ViewModel
 */
public class ShopCodeItemViewModel extends BaseObservable {

    private ShopBean bean;
    private Context context;

    public ShopCodeItemViewModel(Context context, ShopBean bean){
        this.bean = bean;
        this.context = context;
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(context, ShopInformationActivity.class);
        intent.putExtra("userId", bean.id);
        context.startActivity(intent);

    }
    public void onclickShare(View view) {
        bean.scanFlag=false;
        RxBus.get().post(bean);
    }
    public String getName() {
        return bean.name;
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
