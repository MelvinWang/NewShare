package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.User;
import com.melvin.share.ui.activity.selfcenter.ApplyRefundActivity;
import com.melvin.share.ui.activity.selfcenter.WaitSendProductOrderInformationActivity;
import com.melvin.share.dialog.UrgeBillDialog;

/**
 * Created Time: 2016/8/6
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：待发货页面item的ViewModel
 */
public class WaitSendproductItemViewModel extends BaseObservable {

    private User user;
    private Context context;

    public WaitSendproductItemViewModel(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public void onItemClick(View view) {
        context.startActivity(new Intent(context, WaitSendProductOrderInformationActivity.class));
    }

    public void onApplyRefundClick(View view) {
        context.startActivity(new Intent(context, ApplyRefundActivity.class));
    }

    public void urgeBill(View view) {
        final UrgeBillDialog dialog = new UrgeBillDialog(context);
        dialog.setContentView(null);
        dialog.show();
    }

    public String getImgUrl() {
        return "http://h.hiphotos.baidu.com/image/h%3D300/sign=ff62800b073b5bb5a1d726fe06d2d523/a6efce1b9d16fdfa7807474eb08f8c5494ee7b23.jpg";
    }

    public void setEntity(User user) {
        this.user = user;
        notifyChange();
    }
}
