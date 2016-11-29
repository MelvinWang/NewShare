package com.melvin.share.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.melvin.share.ui.fragment.factory.FragmentQrcodeFactory;


/**
 * Created Time: 2016/7/21.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：二维码Adapter
 */
public class QrcodeAdapter extends FragmentPagerAdapter {
    public final static int TAB_COUNT = 2;
    private final static String[] mItems = new String[]{"订单码", "店铺码"};
    private Context context;


    public QrcodeAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        //  通过Fragment工厂  生产Fragment
        return FragmentQrcodeFactory.createFragment(position);
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