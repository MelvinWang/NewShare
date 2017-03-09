package com.melvin.share.ui.activity.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.widget.RadioGroup;

import com.melvin.share.R;
import com.melvin.share.Utils.RxBus;
import com.melvin.share.Utils.StatusBarUtils;
import com.melvin.share.adapter.FragmentAdapter;
import com.melvin.share.databinding.ActivityMainBinding;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.NoScrollViewPager;

/**
 * Created Time: 2016/7/17.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：主页面
 */
public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private Context mContext = null;
    NoScrollViewPager mViewPager;
    RadioGroup mRadioGroup;
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = this;
        initWindow();
        initData();
    }

    /**
     * 沉浸式状态栏
     */
    protected void initWindow() {
        StatusBarUtils.setColor(this, getResources().getColor(R.color.gray_light));
    }


    /**
     * 初始化数据
     */
    private void initData() {
        mViewPager = binding.mViewPager;
        mViewPager.setOffscreenPageLimit(3);
        mRadioGroup = binding.mRadioGroup;
        mRadioGroup.check(R.id.waybill);
        //设置适配器
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);
        //设置选项选中事件
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.waybill:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.query:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.reporting:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.friends:
                        mViewPager.setCurrentItem(3);
                        break;
                }
            }
        });
        mViewPager.setCurrentItem(0);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        killAll();
    }
}
