package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.User;
import com.melvin.share.ui.activity.OderEvaluateActivity;
import com.melvin.share.ui.activity.ProductInfoActivity;
import com.melvin.share.ui.activity.selfcenter.LogisticsInfoActivity;
import com.melvin.share.ui.activity.selfcenter.WaitEvaluateOrderInformationActivity;

/**
 * Created Time: 2016/8/6
 * <p>
 * Author:Melvin
 * <p>
 * 功能：待评价页面item的ViewModel
 */
public class WaitEvaluateItemViewModel extends BaseObservable {

    private User user;
    private Context context;

    public WaitEvaluateItemViewModel(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public void onItemClick(View view) {
        context.startActivity(new Intent(context, WaitEvaluateOrderInformationActivity.class));
    }

    public void onLookLogisticsClick(View view) {
        context.startActivity(new Intent(context, LogisticsInfoActivity.class));
    }

    public void onEvaluateClick(View view) {
        context.startActivity(new Intent(context, OderEvaluateActivity.class));
    }

    public String getImgUrl() {
        return "http://h.hiphotos.baidu.com/image/h%3D300/sign=ff62800b073b5bb5a1d726fe06d2d523/a6efce1b9d16fdfa7807474eb08f8c5494ee7b23.jpg";
    }

    public void setEntity(User user) {
        this.user = user;
        notifyChange();
    }
}
