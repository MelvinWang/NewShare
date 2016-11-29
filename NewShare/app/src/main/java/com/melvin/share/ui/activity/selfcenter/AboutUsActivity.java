package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;

import com.melvin.share.R;
import com.melvin.share.databinding.ActivityAboutUsBinding;
import com.melvin.share.databinding.ActivityQueryHelpBinding;
import com.melvin.share.ui.activity.common.BaseActivity;


/**
 * Created Time: 2016/8/7.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：关于我们
 */

public class AboutUsActivity extends BaseActivity {
    private ActivityAboutUsBinding binding;
    private Context context;


    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us);
        context = this;
        initWindow();
        initToolbar(binding.toolbar);
        initData();
    }

    private void initData() {

    }


}
