package com.melvin.share.ui.activity.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import com.melvin.share.R;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.databinding.ActivitySplashBinding;

/**
 * Created Time: 2017/4/9.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：闪屏页
 */
public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding binding;
    private Context mContext;
    //初始化界面
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mContext = this;
        initWindow();
        startAnim();
    }
    /**
     * 开启动画
     */
    private void startAnim() {
        // 动画集合
        AnimationSet set = new AnimationSet(false);
        // 渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0.5f, 1);
        alpha.setDuration(2000);// 动画时间
        alpha.setFillAfter(true);// 保持动画状态
        set.addAnimation(alpha);
        // 设置动画监听
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画执行结束
            @Override
            public void onAnimationEnd(Animation animation) {
                initData();
            }
        });

        binding.splashRoot.startAnimation(set);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        if (TextUtils.isEmpty(ShapreUtils.getCustomerId())) {
            startActivity(new Intent(mContext, LoginActivity.class));
        } else {
            startActivity(new Intent(mContext, MainActivity.class));
        }
        finish();
    }

}
