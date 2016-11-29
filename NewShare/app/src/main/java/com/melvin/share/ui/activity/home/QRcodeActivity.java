package com.melvin.share.ui.activity.home;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.view.View;

import com.google.zxing.WriterException;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.R;
import com.melvin.share.Utils.RxBus;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.QrcodeAdapter;
import com.melvin.share.databinding.ActivityQrCodeBinding;
import com.melvin.share.popwindow.SelectPicPopupWindow;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.zxing.encoding.EncodingHandler;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/7/17
 * <p>
 * 描述：二维码分享
 */
public class QRcodeActivity extends BaseActivity {
    //自定义的弹出框类
    private SelectPicPopupWindow menuWindow;
    private ActivityQrCodeBinding binding;
    private Context mContext;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qr_code);
        mContext = this;
        initWindow();
        menuWindow = new SelectPicPopupWindow((Activity) mContext, itemsOnClick);
        initTable();
        RxBus.get().register(this); //注册
        initToolbar(binding.toolbar);
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.wechat_share:
                    Utils.showToast(mContext, "wechat_share");
                    break;
                case R.id.friends_share:
                    Utils.showToast(mContext, "friends_share");
                    break;
                case R.id.qq_share:
                    Utils.showToast(mContext, "qq_share");
                    break;
                case R.id.qq_zone_share:
                    Utils.showToast(mContext, "qq_zone_share");
                    break;
                default:
                    break;
            }

        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);//销毁
    }

    @Subscribe
    public void qrcode(String qrcodeString) {
        //显示窗口,设置layout在PopupWindow中显示的位置
        menuWindow.showAtLocation(binding.coordinatorLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        try {
            Bitmap qrCodeBitmap = EncodingHandler.createQRCode(qrcodeString, 1000);
            menuWindow.setImgview(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化标题,绑定
     */
    private void initTable() {
        QrcodeAdapter viewPagerAdapter = new QrcodeAdapter(getSupportFragmentManager(), this);
        binding.viewpager.setAdapter(viewPagerAdapter);//设置适配器
        TabLayout mTabLayout = binding.tablayout;
        mTabLayout.setupWithViewPager(binding.viewpager);
    }


}
