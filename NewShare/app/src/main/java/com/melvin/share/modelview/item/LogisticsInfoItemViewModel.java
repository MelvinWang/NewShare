package com.melvin.share.modelview.item;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.LogisticsModel;
import com.melvin.share.model.User;

/**
 * Created Time: 2016/8/7.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：查看物流页面item的ViewModel
 */
public class LogisticsInfoItemViewModel extends BaseObservable {

    private LogisticsModel.LogistBean.ListBean bean;
    private Context context;

    public LogisticsInfoItemViewModel(Context context, LogisticsModel.LogistBean.ListBean bean) {
        this.bean = bean;
        this.context = context;
    }

    public void onItemClick(View view) {

    }

    public String getRemark() {
        return bean.remark;
    }

    public String getTime() {
        return bean.datetime;
    }


    public void setEntity(LogisticsModel.LogistBean.ListBean bean) {
        this.bean = bean;
        notifyChange();
    }
}
