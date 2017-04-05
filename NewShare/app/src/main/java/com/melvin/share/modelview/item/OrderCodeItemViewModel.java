package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.melvin.share.model.Product;
import com.melvin.share.network.GlobalFlag;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.rx.RxBus;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.ProductInfoActivity;
import com.melvin.share.ui.activity.common.CommonImageActivity;

/**
 * Created Time: 2017/4/5.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：订单码item的ViewModel
 */
public class OrderCodeItemViewModel extends BaseObservable {
    private Product bean;
    private Context context;

    public OrderCodeItemViewModel(Context context, Product bean) {
        this.bean = bean;
        this.context = context;
    }

    public void onItemClick(View view) {
        context.startActivity(new Intent(context, ProductInfoActivity.class));
    }

    public void onImageClick(View view) {
        Intent intent=new Intent(context, CommonImageActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        intent.putExtra("imageUrl",getImgUrl());
        context.startActivity(intent,options.toBundle());
    }
    public void onclickShare(View view) {
        bean.scanFlag=true;
        RxBus.get().post(bean);
    }
    public String getName() {
        return bean.productName;
    }
    public String getImgUrl() {
        String[] split = bean.mainPicture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            return url;
        }
        return "";
    }

    public void setEntity(Product bean) {
        this.bean = bean;
        notifyChange();
    }
}
