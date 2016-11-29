package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.databinding.DataBindingUtil;

import com.melvin.share.R;
import com.melvin.share.databinding.ActivityOpenShopSBinding;
import com.melvin.share.ui.activity.common.BaseActivity;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/7/24
 * <p>
 * 描述： 我要开店第二页
 */
public class OpenshopSecondActivity extends BaseActivity {
    private ActivityOpenShopSBinding binding;
    private Context mContext = null;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_openshop_second);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
    }

    private void ininData() {

    }

}
