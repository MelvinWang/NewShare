package com.melvin.share.ui.activity.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.melvin.share.R;
import com.melvin.share.Utils.CodeUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.LoginAdapter;
import com.melvin.share.adapter.QrcodeAdapter;
import com.melvin.share.databinding.ActivityLoginBinding;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.umeng.socialize.UMShareAPI;


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
    private long firstClickTime;

    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        context = this;
        initWindow();
        initTable();
//        initToolbar(binding.toolbar);
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


    /**
     * 返回键先关闭侧滑菜单
     */
    @Override
    public void onBackPressed() {
        if (firstClickTime > 0) {// 发现之前点击过一次
            if (System.currentTimeMillis() - firstClickTime < 1000) {// 判断两次点击是否小于1000毫秒
                killApp();
                return;
            }
            firstClickTime = 0;//重置时间, 重新开始
        } else {
            firstClickTime = System.currentTimeMillis();
        }
            Utils.showToast(context, "请再点击一次退出");

    }

    /**
     * toolbar上菜单的选择事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                killApp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }


}
