package com.melvin.share.ui.activity.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RadioGroup;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.StatusBarUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.FragmentAdapter;
import com.melvin.share.databinding.ActivityMainBinding;
import com.melvin.share.model.QrcodeShareModel;
import com.melvin.share.rx.RxShareBus;
import com.melvin.share.view.NoScrollViewPager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import static com.umeng.socialize.utils.DeviceConfig.context;

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
    private long firstClickTime;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = this;
        initWindow();
        initData();
//        uMImage = new UMImage(mContext, R.drawable.datu);
//        uMImage.setThumb(new UMImage(mContext, R.drawable.thumb));
        RxShareBus.get().register(this); //注册
    }

    /**
     * 沉浸式状态栏
     */
    protected void initWindow() {
        StatusBarUtils.setColor(this, getResources().getColor(R.color.gray_light));
    }


    //1代表微信  2代表朋友圈  3代表QQ ，4代表空间
    @Subscribe
    public void shareCode(QrcodeShareModel qrcodeShareModel) {
        LogUtils.i(qrcodeShareModel.toString());
        if (3 == qrcodeShareModel.flagCode) {
            try {
                Bitmap qrCodeBitmap = Utils.getBitmap(qrcodeShareModel.code);
                UMImage uMImage = new UMImage(mContext, qrCodeBitmap);
                UMImage thumb = new UMImage(this, R.drawable.thumb);
                uMImage.setThumb(thumb);
                new ShareAction(MainActivity.this).setPlatform(SHARE_MEDIA.QQ)
                        .withMedia(uMImage)
                        .setCallback(umShareListener)
                        .share();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (4 == qrcodeShareModel.flagCode) {
            try {
                Bitmap qrCodeBitmap = Utils.getBitmap(qrcodeShareModel.code);
                UMImage uMImage = new UMImage(this, qrCodeBitmap);
                UMImage thumb = new UMImage(this, R.drawable.thumb);
                uMImage.setThumb(thumb);
                new ShareAction(MainActivity.this).setPlatform(SHARE_MEDIA.QZONE)
                        .withMedia(uMImage)
                        .setCallback(umShareListener)
                        .share();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (1 == qrcodeShareModel.flagCode) {
            try {
                Bitmap qrCodeBitmap = Utils.getBitmap(qrcodeShareModel.code);
                UMImage uMImage = new UMImage(this, qrCodeBitmap);
                UMImage thumb = new UMImage(this, R.drawable.thumb);
                uMImage.setThumb(thumb);
                new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withMedia(uMImage)
                        .setCallback(umShareListener)
                        .share();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (2 == qrcodeShareModel.flagCode) {
            try {
                Bitmap qrCodeBitmap = Utils.getBitmap(qrcodeShareModel.code);
                UMImage uMImage = new UMImage(this, qrCodeBitmap);
                UMImage thumb = new UMImage(this, R.drawable.thumb);
                uMImage.setThumb(thumb);
                new ShareAction(MainActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(uMImage)
                        .setCallback(umShareListener)
                        .share();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.e("plat", "platform" + platform);
            Utils.showToast(MainActivity.this, platform + " 分享成功");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Utils.showToast(MainActivity.this, platform + " 分享失败");
            if (t != null) {
                Log.e("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Utils.showToast(MainActivity.this, platform + " 分享取消");
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


    /**
     * 返回键先关闭侧滑菜单
     */
    @Override
    public void onBackPressed() {
        if (firstClickTime > 0) {// 发现之前点击过一次
            if (System.currentTimeMillis() - firstClickTime < 1000) {// 判断两次点击是否小于1000毫秒
                super.onBackPressed();
                killApp();
                return;
            }
            firstClickTime = 0;//重置时间, 重新开始
        } else {
            firstClickTime = System.currentTimeMillis();
        }
        Utils.showToast(context, "请再点击一次退出");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxShareBus.get().unregister(this);//销毁
    }
}
