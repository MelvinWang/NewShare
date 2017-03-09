package com.melvin.share.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.melvin.share.R;
import com.melvin.share.Utils.Utils;
import com.melvin.share.dialog.OrderCancelDialog;

import java.util.Date;

import static com.melvin.share.R.id.btn_confirm;
import static com.melvin.share.R.id.sex_cancel;

/**
 * Created Time: 2016/11/24.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：时间选择
 */
public class SelectTimeopupWindow extends PopupWindow {

    private View mMenuView;
    private Button btn_confirm;
    private MyWheelDatePicker main;

    @SuppressLint("InflateParams")
    public SelectTimeopupWindow(final Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_dialog_time, null);
        main = (MyWheelDatePicker) mMenuView.findViewById(R.id.main);
        btn_confirm = (Button) mMenuView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.confirm(main.getStringCurrentDate());
            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
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

    public interface OnCliclListener {
        void confirm(String date);

    }

    public OnCliclListener mOnClickListener;

    public void setOnClickListener(OnCliclListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

}
