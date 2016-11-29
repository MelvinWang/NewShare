package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;
import android.widget.CompoundButton;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.RxBus;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.ProductInfoActivity;

/**
 * Created Time: 2016/8/7.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：购物车编辑页面item的ViewModel
 */
public class ShopCarEditItemViewModel extends BaseObservable {

    private Product product;
    private Context context;
    private String number = "1";

    public ShopCarEditItemViewModel(Context context, Product product) {
        this.product = product;
        this.context = context;
    }

    public void onItemClick(View view) {
        context.startActivity(new Intent(context, ProductInfoActivity.class));
    }

    public String getProductName() {
        return product.repertoryName;
    }

    public String getPrice() {
        return "￥" + product.price;
    }

    public String getNumber() {
        return "x" + product.productNumber;
    }

    public boolean getIsChecked() {
        return product.isChecked;
    }

    public void setIsChecked(boolean b) {
        product.isChecked = b;
    }

    public void onAddClick(View view) {
        this.number = Integer.parseInt(number) + 1 + "";
        notifyChange();

    }

    public void onDeleteClick(View view) {
        this.number = ((Integer.parseInt(number) - 1) == 0) ? "0" : (Integer.parseInt(number) - 1 + "");
        notifyChange();
    }

    public void oncheckProduct(View view) {
        setIsChecked(!product.isChecked);
        notifyChange();
    }


    public String getImgUrl() {
        String[] split = product.picture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            LogUtils.e("哈哈" + url);
            return url;
        }
        return "";
    }

    public void setEntity(Product product) {
        this.product = product;
        notifyChange();
    }
}
