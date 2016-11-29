package com.melvin.share.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import com.melvin.share.R;

/**
 * Created Time: 2016/8/7.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：催单
 */
public class UrgeBillDialog extends Dialog implements View.OnClickListener {
    private View root;


    public UrgeBillDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        root = getLayoutInflater().inflate(R.layout.urge_bill_dialog, null);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(root);
    }

    @Override
    public void onClick(View v) {
    }

}