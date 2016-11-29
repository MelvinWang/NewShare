package com.melvin.share.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.melvin.share.R;
import com.melvin.share.Utils.RxCommonBus;

/**
 * Created Time: 2016/8/10.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：退款类型选择窗口
 */
public class RefundTypePopupWindow extends PopupWindow {


    private Button refund_type_cancel;
    private Button refund_type_confirm;
    private View mMenuView;
    private final RadioGroup refund_radio_group;

    public RefundTypePopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.refund_type_dialog, null);
        refund_type_cancel = (Button) mMenuView.findViewById(R.id.refund_type_cancel);
        refund_type_confirm = (Button) mMenuView.findViewById(R.id.refund_type_confirm);
        refund_radio_group = (RadioGroup) mMenuView.findViewById(R.id.refund_radio_group);
        //设置按钮监听
        refund_type_cancel.setOnClickListener(itemsOnClick);
        refund_type_confirm.setOnClickListener(itemsOnClick);
        //设置选项选中事件
        refund_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.refund_type_refund:
                        RxCommonBus.get().post("我要退款");
                        break;
                    case R.id.refund_type_refundp:
                        RxCommonBus.get().post("我要退货");
                        break;
                }
            }
        });
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

}
