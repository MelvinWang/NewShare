package com.melvin.share.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v4.view.MotionEventCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created Time: 2016/7/17.
 * <p>
 * Author:Melvin
 * <p>
 * 功能： Toast,Share等实用工具
 */
public class Utils {
    public static Toast mToast;
    public static SharedPreferences mPref;

    public static void showToast(Context mContext, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 获取share
     */
    public static SharedPreferences getShare(Context mContext) {
        mPref = mContext.getSharedPreferences("config", mContext.MODE_PRIVATE);
        return mPref;
    }

    /**
     * 获取token
     */
    public static String getToken(Context mContext) {
        mPref = getShare(mContext);
        String token = mPref.getString("token", null);
        return token;
    }


    /**
     * dip转换px
     */
    public static int dip2px(int dip, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */

    public static int px2dip(int px, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * dip 转换成 px
     *
     * @param dip
     * @param context
     * @return
     */
    public static float dip2Dimension(float dip, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
    }

    public static Bitmap getBitmap(String qrcode) {
        Bitmap bitmap = null;
        try {
            Map hints = new Hashtable();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);//纠错能力低（7%）
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrcode, BarcodeFormat.QR_CODE, 400, 400, hints);
            //产生位图
            int w = bitMatrix.getWidth();
            int h = bitMatrix.getHeight();
            int[] data = new int[w * h];
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y))
                        data[y * w + x] = 0xff000000;// 黑色
                    else
                        data[y * w + x] = -1;// -1 相当于0xffffffff 白色
                }
            }
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(data, 0, w, 0, 0, w, h);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

}
