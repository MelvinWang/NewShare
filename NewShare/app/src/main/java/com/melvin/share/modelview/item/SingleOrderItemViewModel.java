package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.view.View;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.ShopInformationActivity;

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

    public void onCrateCode(View view) {
        Utils.showToast(context, "生成二维码");
    }

    public String getName() {
        return bean.productName;
    }

    public String getTotal() {
        return "￥" + bean.total;
    }

    public String getTotalNum() {
        return "X" + bean.totalNum;
    }

    public boolean getCodeShow() {
        try {
            if (TextUtils.isEmpty(bean.orderItemStatus) && Integer.parseInt(bean.orderItemStatus) > 1) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
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
