package com.melvin.share.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.melvin.share.ui.fragment.factory.FragmentAllOrderFactory;


/**
 * Created Time: 2016/11/29.
 * <p>
 * Author:Melvin
 * <p>
 * 功能： 全部订单页面Adapter
 */
public class AllOrderAdapter extends FragmentPagerAdapter {
    private int TAB_COUNT = 6;
    private String[] mItems = new String[]{"全部", "待付款", "待发货", "待收货", "待评价", "退款"};
    private FragmentAllOrderFactory fragmentBillFactory;


    public AllOrderAdapter(FragmentManager fm) {
        super(fm);
        fragmentBillFactory = new FragmentAllOrderFactory();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentBillFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mItems[position];
    }
}