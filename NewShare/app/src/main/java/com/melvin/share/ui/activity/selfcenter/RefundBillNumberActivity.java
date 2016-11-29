package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.melvin.share.R;
import com.melvin.share.databinding.ActivityApplyRefundBinding;
import com.melvin.share.databinding.ActivityRefundBillNumberBinding;
import com.melvin.share.dialog.RefundDialog;
import com.melvin.share.ui.activity.common.BaseActivity;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/9
 * <p>
 * 描述： 申请退款,填写运单号
 */
public class RefundBillNumberActivity extends BaseActivity {
    private ActivityRefundBillNumberBinding binding;
    private Context mContext = null;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refund_bill_number);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
    }

    private void ininData() {

    }

    /**
     * 完成
     */
    public void done(View v) {

    }

}
