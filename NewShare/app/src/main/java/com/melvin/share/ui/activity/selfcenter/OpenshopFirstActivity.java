package com.melvin.share.ui.activity.selfcenter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.melvin.share.R;
import com.melvin.share.databinding.ActivityOpenShopFBinding;
import com.melvin.share.ui.activity.common.BaseActivity;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/7/24
 * <p>
 * 描述： 我要开店第一页
 */
public class OpenshopFirstActivity extends BaseActivity {
    private ActivityOpenShopFBinding binding;
    private Context context = null;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_openshop_first);
        context = this;
        initWindow();
        initToolbar(binding.toolbar);
    }

    private void ininData() {

    }

    /**
     * 下一步
     *
     * @param view
     */
    public void clickNext(View view) {
        startActivity(new Intent(context, OpenshopSecondActivity.class));
    }

}
