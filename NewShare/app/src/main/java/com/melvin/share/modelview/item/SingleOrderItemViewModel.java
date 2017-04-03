package com.melvin.share.modelview.item;

import android.content.Context;
import android.databinding.BaseObservable;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.network.GlobalUrl;

/**
 * Created Time: 2017/4/1.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：全部订单单个商品页面item的ViewModel
 */
public class SingleOrderItemViewModel extends BaseObservable {

    private WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean;
    private Context context;

    public SingleOrderItemViewModel(Context context, WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean) {
        this.bean = bean;
        notifyChange();
        this.context = context;
    }


    public String getName() {
        return bean.productName;
    }
    public String getTotal() {
        return "￥"+bean.total;
    }
    public String getTotalNum() {
        return "X"+bean.totalNum;
    }

    public String getImgUrl() {
        String[] split = bean.mainPicture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            return url;
        }
        return "";
    }
    public void setEntity(WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean) {
        this.bean = bean;
        notifyChange();
    }
}
