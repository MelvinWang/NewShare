package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.Utils.Utils;
import com.melvin.share.dialog.OrderCancelDialog;
import com.melvin.share.dialog.PaySuccessDialog;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.ProductInfoActivity;
import com.melvin.share.ui.activity.selfcenter.WaitPayOrderInformationActivity;

/**
 * Created Time: 2016/8/4.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：待付款item的ViewModel
 */
public class WaitPayItemViewModel extends BaseObservable {

    private User user;
    private Context context;

    public WaitPayItemViewModel(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    public void onItemClick(View view) {
        context.startActivity(new Intent(context, WaitPayOrderInformationActivity.class));
    }

    public void goToPay(View view) {
        final PaySuccessDialog dialog = new PaySuccessDialog(context);
        dialog.setContentView(null);
        dialog.show();
    }

    /**
     * 取消订单
     */
    public void cancelPay(View view) {
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
                Utils.showToast(context, "cancel");

            }
        });
    }


    public String getImgUrl() {
        return "http://h.hiphotos.baidu.com/image/h%3D300/sign=ff62800b073b5bb5a1d726fe06d2d523/a6efce1b9d16fdfa7807474eb08f8c5494ee7b23.jpg";
    }

    public void setEntity(User user) {
        this.user = user;
        notifyChange();
    }
}
