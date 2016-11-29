package com.melvin.share.ui.activity.common;

import android.databinding.DataBindingUtil;

import com.melvin.share.R;
import com.melvin.share.databinding.ActivityAmendPhoneBinding;
import com.melvin.share.databinding.ActivityForgetPasswordBinding;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/7
 * <p/>
 * 描述：忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity {
    private ActivityForgetPasswordBinding binding;

    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
        initWindow();
        initToolbar(binding.toolbar);
        initData();
    }

    private void initData() {

    }

}
