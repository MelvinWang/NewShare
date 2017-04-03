package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.melvin.share.adapter.HotLabelAdapter;
import com.melvin.share.adapter.SingleOrderAdapter;
import com.melvin.share.model.User;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.ui.activity.OderEvaluateActivity;
import com.melvin.share.ui.activity.selfcenter.LogisticsInfoActivity;
import com.melvin.share.ui.activity.selfcenter.WaitEvaluateOrderInformationActivity;

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
        context.startActivity(new Intent(context, WaitEvaluateOrderInformationActivity.class));
    }

    public String getOrderNumber() {
        return "订单号：" + bean.orderNumber;
    }

    public String getOrderStatus() {
        return bean.orderStatus;
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

    public void onLookLogisticsClick(View view) {
        context.startActivity(new Intent(context, LogisticsInfoActivity.class));
    }

    public void onEvaluateClick(View view) {
        context.startActivity(new Intent(context, OderEvaluateActivity.class));
    }


    public void setEntity(WaitPayOrderInfo.OrderBean bean) {
        this.bean = bean;
        setRecyclerData();
        notifyChange();
    }
}
