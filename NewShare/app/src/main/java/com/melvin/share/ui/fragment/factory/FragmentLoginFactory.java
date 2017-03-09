package com.melvin.share.ui.fragment.factory;

import android.support.v4.app.Fragment;

import com.melvin.share.ui.fragment.login.NormalLoginFragment;
import com.melvin.share.ui.fragment.login.PhoneLoginFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created Time: 2017/3/8.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能： 登录
 */
public class FragmentLoginFactory {

    private static Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

    public static Fragment createFragment(int position) {
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
    }

}


