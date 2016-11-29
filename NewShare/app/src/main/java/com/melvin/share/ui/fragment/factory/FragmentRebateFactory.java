package com.melvin.share.ui.fragment.factory;

import android.support.v4.app.Fragment;

import com.melvin.share.ui.fragment.rebate.UsableFragment;
import com.melvin.share.ui.fragment.rebate.WillUsableFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created Time: 2016/7/21.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能： 我的返利
 */
public class FragmentRebateFactory {


    public static Fragment createFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            return fragment = new UsableFragment();
        } else
            return fragment = new WillUsableFragment();
    }

}


