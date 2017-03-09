package com.melvin.share.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.melvin.share.ui.fragment.login.NormalLoginFragment;
import com.melvin.share.ui.fragment.login.PhoneLoginFragment;

import java.util.HashMap;
import java.util.Map;


/**
 * Created Time: 2017/3/8.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：登录Adapter
 */
public class LoginAdapter extends FragmentPagerAdapter {
    public final static int TAB_COUNT = 2;
    private final static String[] mItems = new String[]{"账号登录", "验证登录"};
    private Context context;
    private Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

    public LoginAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        fragment = mFragments.get(position);
        if (fragment == null) {
            if (position == 0) {
                fragment = new NormalLoginFragment();
            } else if (position == 1) {
                fragment = new PhoneLoginFragment();
            }
            if (fragment != null) {
                mFragments.put(position, fragment);
            }
        }
        return fragment;

//        //  通过Fragment工厂  生产Fragment
//        return FragmentLoginFactory.createFragment(position);
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