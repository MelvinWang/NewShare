package com.melvin.share.popwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.melvin.share.R;
import com.melvin.share.Utils.Utils;

/**
 * Created Time: 2016/7/24.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：商品购买窗口
 */
public class PurchasePopupWindow extends PopupWindow {


    private Button purchase_confirm;
    private View mMenuView;
    public final LinearLayout linearLayout1;
    public final TextView textName1;
    public final RecyclerView recyclerView1;
    public final LinearLayout linearLayout2;
    public final TextView textName2;
    public final RecyclerView recyclerView2;
    public final LinearLayout linearLayout3;
    public final TextView textName3;
    public final RecyclerView recyclerView3;
    public final LinearLayout linearLayout4;
    public final TextView textName4;
    public final RecyclerView recyclerView4;
    public final TextView productName;
    public final TextView price;
    public final TextView store;

    public final TextView delete;
    public final TextView number;
    public final TextView add;
    private final ImageView dismissImg;
    public int productNumber = 1;

    public PurchasePopupWindow(final Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.purchase_dialog, null);
        purchase_confirm = (Button) mMenuView.findViewById(R.id.purchase_confirm);
        //数量及增减
        delete = (TextView) mMenuView.findViewById(R.id.delete);
        number = (TextView) mMenuView.findViewById(R.id.number);
        add = (TextView) mMenuView.findViewById(R.id.add);
        dismissImg = (ImageView) mMenuView.findViewById(R.id.dismiss);
        //商品名称，价格，库存
        productName = (TextView) mMenuView.findViewById(R.id.product_name);
        price = (TextView) mMenuView.findViewById(R.id.price);
        store = (TextView) mMenuView.findViewById(R.id.store);

        //第一个
        linearLayout1 = (LinearLayout) mMenuView.findViewById(R.id.linearLayout1);
        textName1 = (TextView) mMenuView.findViewById(R.id.text_name1);
        recyclerView1 = (RecyclerView) mMenuView.findViewById(R.id.recyclerView1);
        //第二个
        linearLayout2 = (LinearLayout) mMenuView.findViewById(R.id.linearLayout2);
        textName2 = (TextView) mMenuView.findViewById(R.id.text_name2);
        recyclerView2 = (RecyclerView) mMenuView.findViewById(R.id.recyclerView2);
        //第三个
        linearLayout3 = (LinearLayout) mMenuView.findViewById(R.id.linearLayout3);
        textName3 = (TextView) mMenuView.findViewById(R.id.text_name3);
        recyclerView3 = (RecyclerView) mMenuView.findViewById(R.id.recyclerView3);
        //第四个
        linearLayout4 = (LinearLayout) mMenuView.findViewById(R.id.linearLayout4);
        textName4 = (TextView) mMenuView.findViewById(R.id.text_name4);
        recyclerView4 = (RecyclerView) mMenuView.findViewById(R.id.recyclerView4);
        //设置按钮监听
        purchase_confirm.setOnClickListener(itemsOnClick);
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productNumber == 1) {
                    Utils.showToast(context, "购买数量至少为1");
                } else {
                    productNumber--;
                    number.setText(productNumber + "");
                }
            }
        });
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                productNumber++;
                number.setText(productNumber + "");
            }
        });
        dismissImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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
