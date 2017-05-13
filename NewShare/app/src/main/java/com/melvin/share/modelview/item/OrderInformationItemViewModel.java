package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.view.View;

import com.melvin.share.Utils.Utils;
import com.melvin.share.dialog.ConfirmReceiveDialog;
import com.melvin.share.dialog.OrderCancelDialog;
import com.melvin.share.dialog.UrgeBillDialog;
import com.melvin.share.model.AllOrderBusFlag;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.rx.RxBus;
import com.melvin.share.model.User;
import com.melvin.share.rx.RxOrderBus;
import com.melvin.share.ui.activity.OderEvaluateActivity;
import com.melvin.share.ui.activity.order.RefundActivity;
import com.melvin.share.ui.activity.selfcenter.ApplyRefundActivity;
import com.melvin.share.ui.activity.selfcenter.LogisticsInfoActivity;

/**
 * Created Time: 2017/4/7.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：订单信息item的ViewModel
 */
public class OrderInformationItemViewModel extends BaseObservable {

    private WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean;
    private Context context;

    public OrderInformationItemViewModel(Context context, WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean) {
        this.bean = bean;
        this.context = context;
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

    public String getImgUrl() {
        String[] split = bean.mainPicture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            return url;
        }
        return "";
    }



    public boolean getRefundStatus() {
        if (TextUtils.equals("2", bean.orderItemStatus) || TextUtils.equals("3", bean.orderItemStatus)) {
            return true;
        }
        return false;
    }

    public boolean getLogisticsStatus() {
        if (TextUtils.equals("3", bean.orderItemStatus) || TextUtils.equals("4", bean.orderItemStatus)) {
            return true;
        }
        return false;
    }

    public boolean getReminderStatus() {
        if (TextUtils.equals("2", bean.orderItemStatus)) {
            return true;
        }
        return false;
    }

    public boolean getConfirmReceiveStatus() {
        if (TextUtils.equals("3", bean.orderItemStatus)) {
            return true;
        }
        return false;
    }

    public boolean getEvaluateStatus() {
        if (TextUtils.equals("4", bean.orderItemStatus)) {
            return true;
        }
        return false;
    }


    //退款
    public void onRefundClick(View view) {
        context.startActivity(new Intent(context, ApplyRefundActivity.class));
    }

    //查看物流
    public void onLookLogisticsClick(View view) {
        Intent intent = new Intent(context, LogisticsInfoActivity.class);
        intent.putExtra("orderItemId", bean.id);
        context.startActivity(intent);

    }


    //催单
    public void onReminderClick(View view) {
        final UrgeBillDialog dialog = new UrgeBillDialog(context);
        dialog.setContentView(null);
        dialog.show();
    }

    //确认收货
    public void onConfirmReceiveClick(View view) {
        final ConfirmReceiveDialog dialog = new ConfirmReceiveDialog(context);
        dialog.setContentView(null);
        dialog.show();
        dialog.setOnClickListener(new ConfirmReceiveDialog.OnCliclListener() {
            @Override
            public void confirm() {
                AllOrderBusFlag allOrderBusFlg=new AllOrderBusFlag();
                allOrderBusFlg.id=bean.id;
                allOrderBusFlg.flagId=1;
                RxOrderBus.get().post(allOrderBusFlg);

            }

            @Override
            public void cancel() {


            }
        });
    }

    //评价
    public void onEvaluateClick(View view) {
        Intent intent = new Intent(context, OderEvaluateActivity.class);
        intent.putExtra("orderItemResponsesBean", bean);
        context.startActivity(intent);
    }


    public void setEntity(WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean) {
        this.bean = bean;
        notifyChange();
    }
}
