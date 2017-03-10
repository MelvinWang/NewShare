package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.SearchBean;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.ui.activity.SearchProductActivity;

/**
 * Created Time: 2017/3/10.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：分享热度标签item的ViewModel
 */
public class HotLabelItemViewModel extends BaseObservable {

    private HomeHotProduct.LabelsBean bean;
    private Context context;

    public HotLabelItemViewModel(Context context, HomeHotProduct.LabelsBean bean) {
        this.bean = bean;
        this.context = context;
    }


    public String getLabelName() {
        return bean.labelName;
    }

    public void setEntity(HomeHotProduct.LabelsBean bean) {
        this.bean = bean;
        notifyChange();
    }
}
