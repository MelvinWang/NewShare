package com.melvin.share.ui.activity.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;

import com.melvin.share.R;
import com.melvin.share.Utils.CodeUtils;
import com.melvin.share.adapter.LoginAdapter;
import com.melvin.share.adapter.QrcodeAdapter;
import com.melvin.share.databinding.ActivityLoginBinding;
import com.melvin.share.ui.activity.common.BaseActivity;


/**
 * Created Time: 2017/3/8.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：登录页面
 */

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private Context context;
    //产生的验证码
    private String realCode;
    private ImageView iv_showCode;

    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        context = this;
        initWindow();
        initTable();
        initToolbar(binding.toolbar);
    }

    /**
     * 初始化标题,绑定
     */
    private void initTable() {
        LoginAdapter viewPagerAdapter = new LoginAdapter(getSupportFragmentManager(), this);
        binding.viewpager.setAdapter(viewPagerAdapter);//设置适配器
        TabLayout mTabLayout = binding.tablayout;
        mTabLayout.setupWithViewPager(binding.viewpager);
    }



}
