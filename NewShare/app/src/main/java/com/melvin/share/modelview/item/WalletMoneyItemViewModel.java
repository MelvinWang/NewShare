package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.melvin.share.model.WalletProduct;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.rx.RxBus;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.ProductInfoActivity;
import com.melvin.share.ui.activity.common.CommonImageActivity;

/**
 * Created Time: 2016/11/29.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：钱包金额item的Adapter
 */

public class WalletMoneyItemViewModel extends BaseObservable {


    private WalletProduct model;
    private Context context;

    public WalletMoneyItemViewModel(Context context, WalletProduct model) {
        this.model = model;
        this.context = context;
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(context, ProductInfoActivity.class);
        intent.putExtra("productId",model.productId);
        context.startActivity(intent);
    }

    public void onImageClick(View view) {
        Intent intent = new Intent(context, CommonImageActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        intent.putExtra("imageUrl", model.mainPicture);
        context.startActivity(intent, options.toBundle());
    }

    public String getName() {
        return model.productName;
    }
    public String getUserName() {
        return model.username;
    }
    public String getPrice() {
        return model.price;
    }

    public String getImgUrl() {
        String[] split = model.mainPicture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            return url;
        }
        return "";
    }

    public void setEntity(WalletProduct model) {
        this.model = model;
        notifyChange();
    }
}
