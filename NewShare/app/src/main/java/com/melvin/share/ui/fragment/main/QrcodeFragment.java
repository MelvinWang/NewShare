package com.melvin.share.ui.fragment.main;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.WriterException;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.melvin.share.R;
import com.melvin.share.network.GlobalFlag;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.rx.RxBus;
import com.melvin.share.Utils.Utils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.QrcodeAdapter;
import com.melvin.share.databinding.FragmentQrCodeBinding;
import com.melvin.share.dialog.QrCodeShareDialog;
import com.melvin.share.popwindow.SelectPicPopupWindow;
import com.melvin.share.zxing.encoding.EncodingHandler;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/3/7
 * <p/>
 * 描述：生成二维码
 */
public class QrcodeFragment extends BaseFragment {
    //自定义的弹出框类
    private SelectPicPopupWindow menuWindow;
    private FragmentQrCodeBinding binding;
    private Context mContext;
    private View root;
    private QrCodeShareDialog dialog;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qr_code, container, false);
        if (root == null) {
            mContext = getActivity();
            LogUtils.i("QrcodeFragment+initView");
            dialog = new QrCodeShareDialog(mContext);
            dialog.setContentView(null);
            menuWindow = new SelectPicPopupWindow((Activity) mContext, itemsOnClick);
            RxBus.get().register(this); //注册
            initTable();
            root = binding.getRoot();
        } else {
            ViewUtils.removeParent(root);
        }
        return root;
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
                case R.id.cancel:
                    Utils.showToast(mContext, "cancel");
                    break;
                default:
                    break;
            }

        }

    };


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);//销毁
    }

    @Subscribe
    public void qrcode(final String qrcodeString) {
        LogUtils.i("QrcodeFragment+qrcode" + qrcodeString);
        dialog.setOnClickListener(new QrCodeShareDialog.OnCliclListener() {
            @Override
            public void share() {
                sharePlace(qrcodeString);
            }
        });
        //订单
        if (TextUtils.equals(GlobalFlag.ordercodeFlag, qrcodeString)) {
            dialog.setTitle("订单");
            dialog.setDetail("订单");
        } else if (TextUtils.equals(GlobalFlag.shopcodeFlag, qrcodeString)) {
            dialog.setTitle("商店");
            dialog.setDetail("商店");
        }
        dialog.show();
        try {
            Bitmap qrCodeBitmap = EncodingHandler.createQRCode(qrcodeString, 1000);
            dialog.setImgview(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    //分享信息
    public void sharePlace(String qrcodeString) {
        //显示窗口,设置layout在PopupWindow中显示的位置
        menuWindow.showAtLocation(binding.coordinatorLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 初始化标题,绑定
     */
    private void initTable() {
        QrcodeAdapter viewPagerAdapter = new QrcodeAdapter(getChildFragmentManager(), getActivity());
        binding.viewpager.setAdapter(viewPagerAdapter);//设置适配器
        TabLayout mTabLayout = binding.tablayout;
        mTabLayout.setupWithViewPager(binding.viewpager);
    }

}