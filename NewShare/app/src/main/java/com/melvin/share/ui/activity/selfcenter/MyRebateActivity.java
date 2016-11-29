package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;

import com.melvin.share.R;
import com.melvin.share.adapter.MyRebateAdapter;
import com.melvin.share.databinding.ActivityMyRebateBinding;
import com.melvin.share.ui.activity.common.BaseActivity;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/21
 * <p/>
 * 描述：我的返利
 */
public class MyRebateActivity extends BaseActivity {
    private ActivityMyRebateBinding binding;
    private Context mContext = null;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_rebate);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        initTable();
    }

    /**
     * 初始化标题,绑定
     */
    private void initTable() {
        MyRebateAdapter viewPagerAdapter = new MyRebateAdapter(getSupportFragmentManager(), this);
        binding.viewpager.setAdapter(viewPagerAdapter);//设置适配器
        TabLayout mTabLayout = binding.tablayout;
        mTabLayout.setupWithViewPager(binding.viewpager);
    }


}


