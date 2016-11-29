package com.melvin.share.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.melvin.share.R;

/**
 * Created Time: 2016/8/7.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：订单取消弹
 */
public class OrderCancelDialog extends Dialog implements View.OnClickListener {
    private View root;
    private final TextView tvCancel;
    private final TextView tvConfirm;


    public OrderCancelDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        root = getLayoutInflater().inflate(R.layout.order_cancel_dialog, null);
        tvCancel = (TextView) root.findViewById(R.id.cancel);
        tvConfirm = (TextView) root.findViewById(R.id.confirm);

        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(root);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                mOnClickListener.cancel();
                OrderCancelDialog.this.dismiss();
                break;
            case R.id.confirm:
                mOnClickListener.confirm();
                OrderCancelDialog.this.dismiss();
                break;
        }
    }

    public interface OnCliclListener {
        void confirm();
        void cancel();

    }

    public OnCliclListener mOnClickListener;

    public void setOnClickListener(OnCliclListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}