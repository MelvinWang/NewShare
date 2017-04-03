package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.melvin.share.Utils.ConvertUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.SingleOrderAdapter;
import com.melvin.share.dialog.ConfirmReceiveDialog;
import com.melvin.share.dialog.OrderCancelDialog;
import com.melvin.share.dialog.UrgeBillDialog;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.ui.activity.OderEvaluateActivity;
import com.melvin.share.ui.activity.order.WaitEvaluateOrderInformationActivity;
import com.melvin.share.ui.activity.order.WaitPayOrderActivity;
import com.melvin.share.ui.activity.order.WaitReceiveProductOrderInformationActivity;
import com.melvin.share.ui.activity.order.WaitSendProductOrderInformationActivity;
import com.melvin.share.ui.activity.selfcenter.LogisticsInfoActivity;
import com.melvin.share.ui.activity.order.RefundActivity;


/**
 * Created Time: 2017/4/1
 * <p>
 * Author:Melvin
 * <p>
 * 功能：全部订单页面item的ViewModel
 */
public class AllOrderItemViewModel extends BaseObservable {

    private WaitPayOrderInfo.OrderBean bean;
    private Context context;
    private RecyclerView recyclerView;

    public AllOrderItemViewModel(Context context, WaitPayOrderInfo.OrderBean bean, RecyclerView recyclerView) {
        this.bean = bean;
        this.context = context;
        this.recyclerView = recyclerView;
        setRecyclerData();
    }

    private void setRecyclerData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        SingleOrderAdapter adpter = new SingleOrderAdapter(context, bean.orderItemResponses);
        recyclerView.setAdapter(adpter);

    }

    public void onItemClick(View view) {
        if (TextUtils.equals("1", bean.orderStatus)) {//待付款
            Intent intent = new Intent(context, WaitPayOrderActivity.class);
            intent.putExtra("fromAllOrder",true);
            intent.putExtra("orderId",bean.id);
            context.startActivity(intent);
        } else if (TextUtils.equals("2", bean.orderStatus)) {//待发货
            context.startActivity(new Intent(context, WaitSendProductOrderInformationActivity.class));

        } else if (TextUtils.equals("3", bean.orderStatus)) {//待收货
            context.startActivity(new Intent(context, WaitReceiveProductOrderInformationActivity.class));

        } else if (TextUtils.equals("4", bean.orderStatus)) {//待评价
            context.startActivity(new Intent(context, WaitEvaluateOrderInformationActivity.class));

        } else if (TextUtils.equals("5", bean.orderStatus)) {//退款
            context.startActivity(new Intent(context, RefundActivity.class));

        }
    }

    public String getOrderNumber() {
        return "订单号：" + bean.orderNumber;
    }

    public String getOrderStatus() {

        return ConvertUtils.statusIdToName(bean.orderStatus);
    }

    public String getTotalNum() {
        return "共" + bean.totalNum + "件商品";
    }

    public String getTotal() {
        return "￥ " + bean.total;
    }

    public String getPostage() {
        return "(含运费" + bean.postage + ")";
    }


    public boolean getCancelOrderStatus() {
        if (TextUtils.equals("1", bean.orderStatus)) {
            return true;
        }
        return false;
    }

    public boolean getRefundStatus() {
        if (TextUtils.equals("2", bean.orderStatus) || TextUtils.equals("3", bean.orderStatus)) {
            return true;
        }
        return false;
    }

    public boolean getLogisticsStatus() {
        if (TextUtils.equals("3", bean.orderStatus) || TextUtils.equals("4", bean.orderStatus)) {
            return true;
        }
        return false;
    }

    public boolean getPayStatus() {
        if (TextUtils.equals("1", bean.orderStatus)) {
            return true;
        }
        return false;
    }

    public boolean getReminderStatus() {
        if (TextUtils.equals("2", bean.orderStatus)) {
            return true;
        }
        return false;
    }

    public boolean getConfirmReceiveStatus() {
        if (TextUtils.equals("3", bean.orderStatus)) {
            return true;
        }
        return false;
    }

    public boolean getEvaluateStatus() {
        if (TextUtils.equals("4", bean.orderStatus)) {
            return true;
        }
        return false;
    }

    //取消订单
    public void onCancelOrderClick(View view) {
        final OrderCancelDialog dialog = new OrderCancelDialog(context);
        dialog.setContentView(null);
        dialog.show();
        dialog.setOnClickListener(new OrderCancelDialog.OnCliclListener() {
            @Override
            public void confirm() {
                Utils.showToast(context, "confirm");
            }

            @Override
            public void cancel() {

            }
        });
    }

    //退款
    public void onRefundClick(View view) {
        context.startActivity(new Intent(context, RefundActivity.class));
    }

    //查看物流
    public void onLookLogisticsClick(View view) {
        context.startActivity(new Intent(context, LogisticsInfoActivity.class));
    }

    //付款
    public void onPayClick(View view) {
        Intent intent = new Intent(context, WaitPayOrderActivity.class);
        intent.putExtra("fromAllOrder",true);
        intent.putExtra("orderId",bean.id);
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
                Utils.showToast(context, "confirm");
            }

            @Override
            public void cancel() {
                Utils.showToast(context, "cancel");

            }
        });
    }

    //评价
    public void onEvaluateClick(View view) {
        context.startActivity(new Intent(context, OderEvaluateActivity.class));
    }


    public void setEntity(WaitPayOrderInfo.OrderBean bean) {
        this.bean = bean;
        setRecyclerData();
        notifyChange();
    }
}
