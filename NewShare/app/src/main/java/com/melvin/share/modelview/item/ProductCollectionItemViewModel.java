package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;
import android.widget.CompoundButton;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.ProductInfoActivity;

/**
 * Created Time: 2017/3/20.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：商品收藏item的ViewModel
 */
public class ProductCollectionItemViewModel extends BaseObservable {

    private Product product;
    private Context context;

    public ProductCollectionItemViewModel(Context context, Product product) {
        this.product = product;
        this.context = context;
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(context, ProductInfoActivity.class);
        intent.putExtra("productId", product.id);
        context.startActivity(intent);
    }

    public void setIsShow(boolean b) {
        product.isShow = b;
    }

    public boolean getIsShow() {
        return product.isShow;
    }

    public void setIsFocus(boolean b) {
        product.isChecked = b;
    }

    public boolean getIsFocus() {
        return product.isChecked;
    }

    public String getProductName() {
        return product.name;
    }

    public String getShareTimes() {
        return product.shareTimes;
    }

    public String getPrice() {
        return "￥" + product.price;
    }

    public String getPlace() {
        return product.place;
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
        String[] split = product.mainPicture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            return url;
        }
        return "";
    }

    public void setEntity(Product product) {
        this.product = product;
        notifyChange();
    }
}
