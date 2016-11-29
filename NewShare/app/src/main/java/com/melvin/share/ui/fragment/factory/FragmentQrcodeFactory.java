package com.melvin.share.ui.fragment.factory;

import android.support.v4.app.Fragment;

import com.melvin.share.ui.fragment.qrcode.OrderCodeFragment;
import com.melvin.share.ui.fragment.qrcode.ShopCodeFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created Time: 2016/7/21.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能： 二维码
 */
public class FragmentQrcodeFactory {

    private static Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

    public static Fragment createFragment(int position) {
        Fragment fragment = null;
        fragment = mFragments.get(position);
        if (fragment == null) {
            if (position == 0) {
                fragment = new OrderCodeFragment();
            } else if (position == 1) {
                fragment = new ShopCodeFragment();
            }
            if (fragment != null) {
                mFragments.put(position, fragment);
            }
        }
        return fragment;
    }

}


