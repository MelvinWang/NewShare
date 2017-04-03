package com.melvin.share.modelview.item;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.model.Product;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.network.GlobalUrl;

import java.math.BigDecimal;

/**
 * Created Time: 2017/4/1.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：订单信息 未付款item的ViewModel
 */
public class WaitpayOrderItemViewModel extends BaseObservable {

    private WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean;
    private Context context;

    public WaitpayOrderItemViewModel(Context context, WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean) {
        this.bean = bean;
        this.context = context;
    }

    public void onItemClick(View view) {

    }

    public String getProductName() {
        return bean.productName;
    }
    public String getProductNumber() {
        return "x"+bean.totalNum;
    }
    public String getPrice() {
        return "￥ "+bean.total;
    }
    public String getImgUrl() {
        String[] split = bean.mainPicture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            LogUtils.e("哈哈" + url);
            return url;
        }
        return "";
    }

    public void setEntity(WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean) {
        this.bean = bean;
        notifyChange();
    }
}
