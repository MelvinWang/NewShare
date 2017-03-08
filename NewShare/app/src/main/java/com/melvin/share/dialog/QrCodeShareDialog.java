package com.melvin.share.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.melvin.share.R;

/**
 * Created Time: 2017/3/8.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：二维码生成分享
 */
public class QrCodeShareDialog extends Dialog implements View.OnClickListener {
    private View root;
    private final ImageView shareImage;
    private final TextView title;
    private final TextView detail;
    private final ImageView qrcodeImage;


    public QrCodeShareDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCanceledOnTouchOutside(true);
        root = getLayoutInflater().inflate(R.layout.qrcode_share_dialog, null);
        shareImage = (ImageView) root.findViewById(R.id.share_image);
        shareImage.setOnClickListener(this);
        qrcodeImage = (ImageView) root.findViewById(R.id.qrcode_image);


        title = (TextView) root.findViewById(R.id.title);
        detail = (TextView) root.findViewById(R.id.detail);


    }

    public void setImgview(Bitmap qrCodeBitmap) {
        qrcodeImage.setImageBitmap(qrCodeBitmap);
    }
    public void setTitle(String sTitle) {
        title.setText(sTitle);
    }
    public void setDetail(String sDetail) {
        detail.setText(sDetail);
    }
    @Override
    public void setContentView(View view) {
        super.setContentView(root);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_image:
                mOnClickListener.share();
                QrCodeShareDialog.this.dismiss();
                break;
        }
    }

    public interface OnCliclListener {
        void share();

    }

    public OnCliclListener mOnClickListener;

    public void setOnClickListener(OnCliclListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}