package com.melvin.share.ui.activity.shopcar;

import android.content.Context;
import android.databinding.DataBindingUtil;

import com.melvin.share.R;
import com.melvin.share.databinding.ActivityDepositBinding;
import com.melvin.share.ui.activity.common.BaseActivity;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/30
 * <p>
 * 描述： 提现
 */
public class DepositActivity extends BaseActivity {
    private ActivityDepositBinding binding;
    private Context mContext = null;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deposit);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
    }

    private void ininData() {

    }

}
