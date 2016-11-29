package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.View;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.R;
import com.melvin.share.Utils.RxCommonBus;
import com.melvin.share.databinding.ActivityApplyRefundBinding;
import com.melvin.share.dialog.RefundDialog;
import com.melvin.share.popwindow.RefundReasonPopupWindow;
import com.melvin.share.popwindow.RefundTypePopupWindow;
import com.melvin.share.ui.activity.common.BaseActivity;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/7
 * <p>
 * 描述： 申请退款
 */
public class ApplyRefundActivity extends BaseActivity {
    private ActivityApplyRefundBinding binding;
    private Context mContext = null;
    private RefundTypePopupWindow refundTypePopupWindow;
    private RefundReasonPopupWindow refundReasonPopupWindow;
    private String refundType;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_apply_refund);
        mContext = this;
        refundTypePopupWindow = new RefundTypePopupWindow(this, itemsOnClick);
        refundReasonPopupWindow = new RefundReasonPopupWindow(this, itemsOnClick);
        RxCommonBus.get().register(this); //注册
        initWindow();
        initToolbar(binding.toolbar);
    }

    private void ininData() {

    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            refundTypePopupWindow.dismiss();
            refundReasonPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.refund_type_cancel:
                    refundTypePopupWindow.dismiss();
                    break;
                case R.id.refund_type_confirm:
                    binding.refundType.setText(refundType);
                    break;
                case R.id.refund_reason_cancel:
                    refundReasonPopupWindow.dismiss();
                    break;
                case R.id.refund_reason_confirm:
                    binding.refundReason.setText(refundType);
                    break;
            }

        }

    };

    @Subscribe
    public void refundTypeSelect(String refundType) {
        this.refundType = refundType;
    }


    /**
     * 选择退款类型
     */
    public void selectRefundType(View v) {
        refundTypePopupWindow.showAtLocation(binding.root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 选择退款原因
     */
    public void selectRefundReason(View v) {
        refundReasonPopupWindow.showAtLocation(binding.root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 提交申请退款
     */
    public void applyRefund(View v) {
        final RefundDialog dialog = new RefundDialog(mContext);
        dialog.setContentView(null);
        dialog.show();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxCommonBus.get().unregister(this);
    }

}
