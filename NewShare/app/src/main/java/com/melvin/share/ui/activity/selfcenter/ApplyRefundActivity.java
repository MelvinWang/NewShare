package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.rx.RxCommonBus;
import com.melvin.share.databinding.ActivityApplyRefundBinding;
import com.melvin.share.dialog.RefundDialog;
import com.melvin.share.popwindow.RefundReasonPopupWindow;
import com.melvin.share.popwindow.RefundTypePopupWindow;
import com.melvin.share.ui.activity.common.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.phoneNumber;

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
    private TextView refundType1;
    private TextView refundReason;
    private EditText refundAmount;
    private EditText refundRemark;

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

        refundType1 = binding.refundType;
        refundReason = binding.refundReason;
        refundAmount = binding.refundAmount;
        refundRemark = binding.refundRemark;


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
        if (TextUtils.isEmpty(refundType1.getText().toString())) {
            Utils.showToast(mContext, "请选择退款类型");
            return;
        }
        if (TextUtils.isEmpty(refundReason.getText().toString())) {
            Utils.showToast(mContext, "请选择退款原因");
            return;
        }
        if (TextUtils.isEmpty(refundAmount.getText().toString())) {
            Utils.showToast(mContext, "请输入退款金额");
            return;
        }
        confirmRefund();
    }

    /**
     * 申请退款
     */
    private void confirmRefund() {
        String scash = refundType1.getText().toString();
        String salipayNumber = refundReason.getText().toString();
        String sphoneNumber = refundAmount.getText().toString();
        String svaliNumber = refundRemark.getText().toString();
        Map map=new HashMap();


        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));




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
