package com.melvin.share.ui.fragment.factory;

import android.support.v4.app.Fragment;

import com.melvin.share.ui.fragment.order.AllOrderFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created Time: 2016/11/29.
 * <p>
 * Author:Melvin
 * <p>
 * 功能： 全部订单单Factory
 */
public class FragmentAllOrderFactory {
    public Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

    public Fragment createFragment(int position) {
        Fragment fragment = null;
        fragment = mFragments.get(position);
        if (fragment == null) {
            if (position == 0) {
                fragment = new AllOrderFragment();
            } else if (position == 1) {
                fragment = new AllOrderFragment();
            } else if (position == 2) {
                fragment = new AllOrderFragment();
            } else if (position == 3) {
                fragment = new AllOrderFragment();
            }else if (position == 4) {
                fragment = new AllOrderFragment();
            }else if (position == 5) {
                fragment = new AllOrderFragment();
            }
            if (fragment != null) {
                mFragments.put(position, fragment);
            }
        }
        return fragment;
    }

}
