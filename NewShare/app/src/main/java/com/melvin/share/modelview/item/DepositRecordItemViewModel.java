package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.model.serverReturn.DepositRecordBean;
import com.melvin.share.rx.RxCommonBus;
import com.melvin.share.ui.activity.selfcenter.NewAddressActivity;

/**
 * Created Time: 2017/7/4.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：提现记录item的ViewModel
 */
public class DepositRecordItemViewModel extends BaseObservable {

    private DepositRecordBean bean;
    private Context context;

    public DepositRecordItemViewModel(Context context, DepositRecordBean bean) {
        this.bean = bean;
        this.context = context;
    }
    public void onItemClick(View view) {

    }

    public String getAccount() {
        return bean.money+"元";
    }

    public String getApplyDate() {
        return bean.applyDate;
    }

    public String getStatus() {
        return bean.status;
    }



    public void setEntity(DepositRecordBean bean) {
        this.bean = bean;
        notifyChange();
    }
}
