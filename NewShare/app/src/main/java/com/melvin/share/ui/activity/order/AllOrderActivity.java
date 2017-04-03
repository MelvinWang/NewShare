package com.melvin.share.ui.activity.order;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.melvin.share.R;
import com.melvin.share.adapter.AllOrderAdapter;
import com.melvin.share.databinding.ActivityAllOrderBinding;
import com.melvin.share.ui.activity.common.BaseActivity;

/**
 * Created Time: 2016/11/29.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：全部订单
 */
public class AllOrderActivity extends BaseActivity {


    private ActivityAllOrderBinding binding;
    private Context mContext;
    public  TabLayout mTabLayout;
    private ViewPager viewpager;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_order);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        initData();
    }
    /**
     * 初始化标题,绑定
     */
    protected void initData() {
        AllOrderAdapter viewPagerAdapter = new AllOrderAdapter(getSupportFragmentManager());
        viewpager = binding.viewpager;
        viewpager.setAdapter(viewPagerAdapter);//设置适配器
        mTabLayout = binding.tablayout;
        mTabLayout.setupWithViewPager(binding.viewpager);
    }
}
