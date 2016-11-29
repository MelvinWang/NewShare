package com.melvin.share.modelview.item;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.RxBus;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.network.GlobalUrl;

import java.math.BigDecimal;

/**
 * Created Time: 2016/8/3.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：确认订单item的ViewModel
 */
public class ConfirmOrderItemViewModel extends BaseObservable {

    private Product product;
    private Context context;

    public ConfirmOrderItemViewModel(Context context,Product product) {
        this.product = product;
        this.context = context;
    }

    public void onItemClick(View view) {

    }

    public String getProductName() {
        return product.repertoryName;
    }
    public String getProductNumber() {
        return "x"+product.productNumber;
    }
    public String getPrice() {
        BigDecimal multiply = new BigDecimal(product.price).multiply(new BigDecimal(product.productNumber));
        return "￥ "+multiply;
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
