package com.melvin.share.ui.activity.common;

import android.databinding.DataBindingUtil;

import com.melvin.share.R;
import com.melvin.share.databinding.ActivityAmendPasswrodBinding;
import com.melvin.share.databinding.ActivityAmendPhoneBinding;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/4
 * <p>
 * 描述：修改手机号码
 */
public class AmendPhoneActivity extends BaseActivity {
    private ActivityAmendPhoneBinding binding;

    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_amend_phone);
        initWindow();
        initToolbar(binding.toolbar);
        initData();
    }

    private void initData() {

    }

}
