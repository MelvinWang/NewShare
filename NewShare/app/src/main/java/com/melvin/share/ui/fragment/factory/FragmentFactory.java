package com.melvin.share.ui.fragment.factory;

import android.support.v4.app.Fragment;

import com.melvin.share.ui.fragment.main.HomeFragment;
import com.melvin.share.ui.fragment.main.SelfFragment;
import com.melvin.share.ui.fragment.main.ShoppingCarFragment;
import com.melvin.share.ui.fragment.main.ShoppingFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created Time: 2016/7/17.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能： 创建四个主页面的工厂
 */
public class FragmentFactory {

    private static Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

    public static Fragment createFragment(int position) {
        Fragment fragment = null;
        fragment = mFragments.get(position);
        if (fragment == null) {
            if (position == 0) {
                fragment = new HomeFragment();
            } else if (position == 1) {
                fragment = new ShoppingFragment();
            } else if (position == 2) {
                fragment = new ShoppingCarFragment();
            } else if (position == 3) {
                fragment = new SelfFragment();
            }
            if (fragment != null) {
                mFragments.put(position, fragment);
            }
        }
        return fragment;
    }

    public static Map<Integer, Fragment> getmFragments() {
        return mFragments;
    }
}
