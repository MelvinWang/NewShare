package com.melvin.share.ui.fragment.factory;

import android.support.v4.app.Fragment;

import com.melvin.share.ui.fragment.productinfo.ProductEvaluateFragment;
import com.melvin.share.ui.fragment.productinfo.ProductInfoFragment;

/**
 * Created Time: 2016/7/24.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能： 商品信息
 */
public class FragmentProductInfoFactory {


    public static Fragment createFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            return fragment = new ProductInfoFragment();
        } else
            return fragment = new ProductEvaluateFragment();
    }

}


