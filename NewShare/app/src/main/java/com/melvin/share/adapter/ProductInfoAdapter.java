package com.melvin.share.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.melvin.share.ui.fragment.factory.FragmentProductInfoFactory;


/**
 * Created Time: 2016/7/24.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：商品信息Adapter
 */
public class ProductInfoAdapter extends FragmentPagerAdapter {
    public final static int TAB_COUNT = 2;
    private final static String[] mItems = new String[]{"商品", "评价"};
    private Context context;


    public ProductInfoAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        //  通过Fragment工厂  生产Fragment
        return FragmentProductInfoFactory.createFragment(position);
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