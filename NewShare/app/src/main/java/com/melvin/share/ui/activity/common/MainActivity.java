package com.melvin.share.ui.activity.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.StatusBarUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.FragmentAdapter;
import com.melvin.share.databinding.ActivityMainBinding;
import com.melvin.share.dialog.QrCodeShareDialog;
import com.melvin.share.model.Product;
import com.melvin.share.rx.RxShareBus;
import com.melvin.share.view.NoScrollViewPager;
import com.melvin.share.zxing.encoding.EncodingHandler;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

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
    private UMImage uMImage;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = this;
        initWindow();
        initData();
        uMImage = new UMImage(mContext,R.drawable.datu);
        uMImage.setThumb(new UMImage(mContext, R.drawable.thumb));
        RxShareBus.get().register(this); //注册
    }

    /**
     * 沉浸式状态栏
     */
    protected void initWindow() {
        StatusBarUtils.setColor(this, getResources().getColor(R.color.gray_light));
    }



    @Subscribe
    public void shareCode( String s) {
        new ShareAction(MainActivity.this).setPlatform(SHARE_MEDIA.QQ)
                .withMedia(uMImage)
                .setCallback(umShareListener)
                .share();

    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.e("plat","platform"+platform);

            Toast.makeText(MainActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.e("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxShareBus.get().unregister(this);//销毁
    }
}
