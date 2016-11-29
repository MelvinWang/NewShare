package com.melvin.share.ui.fragment.factory;

import android.support.v4.app.Fragment;

import com.melvin.share.ui.fragment.wallet.WalletUseFragment;
import com.melvin.share.ui.fragment.wallet.WalletWillFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created Time: 2016/11/29.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能： 钱包
 */
public class FragmentWalletFactory {

    private static Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();

    public static Fragment createFragment(int position) {
        Fragment fragment = null;
        fragment = mFragments.get(position);
        if (fragment == null) {
            if (position == 0) {
                fragment = new WalletUseFragment();
            } else if (position == 1) {
                fragment = new WalletWillFragment();
            }
            if (fragment != null) {
                mFragments.put(position, fragment);
            }
        }
        return fragment;
    }

}


