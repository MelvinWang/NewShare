package com.melvin.share.ui.activity.common;

import android.databinding.DataBindingUtil;

import com.melvin.share.R;
import com.melvin.share.databinding.ActivityAmendPasswrodBinding;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/4
 * <p>
 * 描述：修改密码
 */
public class AmendPasswordActivity extends BaseActivity {
    private ActivityAmendPasswrodBinding binding;

    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_amend_password);
        initWindow();
        initToolbar(binding.toolbar);
        initData();
    }

    private void initData() {

    }

}
