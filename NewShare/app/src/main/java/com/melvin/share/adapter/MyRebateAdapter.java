package com.melvin.share.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.melvin.share.ui.fragment.factory.FragmentRebateFactory;


/**
 * Created Time: 2016/7/21.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：我的返利Adapter
 */
public class MyRebateAdapter extends FragmentPagerAdapter {
    public final static int TAB_COUNT = 2;
    private final static String[] mItems = new String[]{"可用返现", "即将可用"};
    private Context context;


    public MyRebateAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        //  通过Fragment工厂  生产Fragment
        return FragmentRebateFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//得到对应position的Fragment的title
        return mItems[position];
    }
}