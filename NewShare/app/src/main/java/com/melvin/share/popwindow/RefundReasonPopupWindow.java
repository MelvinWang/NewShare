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
 * 功能：退款原因选择窗口
 */
public class RefundReasonPopupWindow extends PopupWindow {


    private Button refund_reason_cancel;
    private Button refund_reason_confirm;
    private View mMenuView;
    private final RadioGroup refund_reason_group;

    public RefundReasonPopupWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.refund_reason_dialog, null);
        refund_reason_cancel = (Button) mMenuView.findViewById(R.id.refund_reason_cancel);
        refund_reason_confirm = (Button) mMenuView.findViewById(R.id.refund_reason_confirm);
        refund_reason_group = (RadioGroup) mMenuView.findViewById(R.id.refund_reason_group);
        //设置按钮监听
        refund_reason_cancel.setOnClickListener(itemsOnClick);
        refund_reason_confirm.setOnClickListener(itemsOnClick);
        //设置选项选中事件
        refund_reason_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.refund_reason_quality:
                        RxCommonBus.get().post("质量问题");
                        break;
                    case R.id.refund_reason_element:
                        RxCommonBus.get().post("成分与商品描述不符");
                        break;
                    case R.id.refund_reason_effect:
                        RxCommonBus.get().post("效果与商品描述不符");
                        break;
                    case R.id.refund_reason_date:
                        RxCommonBus.get().post("生产日期/保质期与商品描述不符");
                        break;
                    case R.id.refund_reason_leak:
                        RxCommonBus.get().post("少发漏件");
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
