package com.melvin.share.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.melvin.share.R;
/**
 * Created Time: 2016/7/24.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：二维码分享窗口
 */
public class SelectPicPopupWindow extends PopupWindow {


    private Button wechat_share, friends_share, qq_share, qq_zone_share;
    private View mMenuView;
    private ImageView qrImgview;

    public SelectPicPopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.alert_dialog, null);
        wechat_share = (Button) mMenuView.findViewById(R.id.wechat_share);
        friends_share = (Button) mMenuView.findViewById(R.id.friends_share);
        qq_share = (Button) mMenuView.findViewById(R.id.qq_share);
        qq_zone_share = (Button) mMenuView.findViewById(R.id.qq_zone_share);
        qrImgview = (ImageView) mMenuView.findViewById(R.id.iv_qr_image);

        //设置按钮监听
        wechat_share.setOnClickListener(itemsOnClick);
        friends_share.setOnClickListener(itemsOnClick);
        qq_share.setOnClickListener(itemsOnClick);
        qq_zone_share.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public void setImgview(Bitmap qrCodeBitmap) {
        qrImgview.setImageBitmap(qrCodeBitmap);
    }

}
