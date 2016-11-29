package com.melvin.share.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.melvin.share.ui.fragment.factory.FragmentWalletFactory;


/**
 * Created Time: 2016/11/29.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：钱包Adapter
 */
public class WalletAdapter extends FragmentPagerAdapter {
    public final static int TAB_COUNT = 2;
    private final static String[] mItems = new String[]{"可用奖励", "即将可用"};
    private Context context;


    public WalletAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    //  通过Fragment工厂  生产Fragment
    @Override
    public Fragment getItem(int position) {
        return FragmentWalletFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    //得到对应position的Fragment的title
    @Override
    public CharSequence getPageTitle(int position) {
        return mItems[position];
    }
}