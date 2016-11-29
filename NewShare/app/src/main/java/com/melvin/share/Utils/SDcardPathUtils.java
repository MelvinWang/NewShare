package com.melvin.share.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.Toast;


import com.melvin.share.app.BaseApplication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created Time: 2016/8/9.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：
 */
public class SDcardPathUtils {
    public static String getSDCardPath() {
        String cmd = "cat/proc/mounts";
        Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                // 获得命令执行后在控制台的输出信息
                if (lineStr.contains("sdcard")
                        && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray != null && strArray.length >= 5) {
                        String result = strArray[1].replace("/.android_secure",
                                "");
                        return result;
                    }
                }
                // 检查命令是否执行失败。
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0表示正常结束，1：非正常结束
                }
            }
            inBr.close();
            in.close();
        } catch (Exception e) {

            return Environment.getExternalStorageDirectory().getPath();
        }

        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 将图片缩小/放大到指定宽高度
     *
     * @param drawable 图片的drawable
     * @param w        指定缩小到的宽度
     * @param h        指定缩小到的高度
     * @param scale    是否保持宽高比，TRUE:将忽略h的值，根据指定宽度自动计算高度 FALSE：根据指定宽度，高度生成图像
     * @return Drawable 返回新生成图片的Drawable
     */

    public static Drawable zoomDrawable(Drawable drawable, int w, int h, Boolean scale) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth;
        float scaleHeight;
        if (scale == true) {
            // 如果要保持宽高比，那说明高度跟宽度的缩放比例都是相同的
            scaleWidth = ((float) w / width);
            scaleHeight = ((float) w / width);
        } else {
            // 如果不保持缩放比，那就根据指定的宽高度进行缩放
            scaleWidth = ((float) w / width);
            scaleHeight = ((float) h / height);
        }
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(null, newbmp);
    }

    /**
     * 根据图片Drawable返回图像
     *
     * @param drawable
     * @return Bitmap bitmap or null ;如果出错，返回NULL
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        try {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                    : Bitmap.Config.RGB_565;
            bitmap = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(BaseApplication.getApplication(), "error:" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

        return bitmap;
    }

}
