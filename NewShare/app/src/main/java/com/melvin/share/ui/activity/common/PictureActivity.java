package com.melvin.share.ui.activity.common;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.melvin.share.R;
import com.melvin.share.Utils.SDcardPathUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.databinding.ActivityChoosePictureBinding;
import com.melvin.share.model.PicturePath;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.view.SelectPicPopupWindow;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.melvin.share.R.id.root;


/**
 * Author: Melvin
 * <p/>
 * Data： 2016/9/1
 * <p/>
 * 描述：
 */
public class PictureActivity extends BaseActivity {
    private ActivityChoosePictureBinding binding;
    private static final int PHOTO = 0;        // 相册选图标记
    private static final int CAMERA = 1;        // 相机拍照标记
    private static final int CUTTING = 2;    // 图片裁切标记
    private Uri imagetempUri;//临时图片路径
    private Uri imageCropUri;//真实图片路径
    private Context mContext;
    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_picture);
        mContext = this;
        //临时图片路径
        File croptempFile = new File(SDcardPathUtils.getSDCardPath() + "/temp.jpg");
        imagetempUri = Uri.fromFile(croptempFile);
        //真实图片路径
        File cropRealFile = new File(SDcardPathUtils.getSDCardPath() + "/real.jpg");
        imageCropUri = Uri.fromFile(cropRealFile);
    }


    /**
     * 照相
     *
     * @param view
     */
    public void takePhotoBtn(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imagetempUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CAMERA);
    }

    /**
     * 选择图片
     *
     * @param view
     */
    public void pickPhotoBtn(View view) {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        // 限制上传到服务器的图片类型："image/jpeg 、 image/png等的类型"
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, PHOTO);
    }

    /**
     * 取消
     *
     * @param view
     */
    public void cancelBtn(View view) {
        finish();
    }


    public void upload(){
        File file = new File(SDcardPathUtils.getSDCardPath() + "/real.jpg");
        RequestBody requestBody=  RequestBody.create(
                MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("picture", file.getName(), requestBody);
        fromNetwork.uploadFile(part)
                .compose(new RxActivityHelper<CommonReturnModel<PicturePath>>().ioMain(PictureActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel<PicturePath>>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel<PicturePath> commonReturnModel) {
                        Utils.showToast(mContext, commonReturnModel.message);
                        returnBack(commonReturnModel.result.path);
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }
    /**
     * 返回上一个页面
     *
     * @param path 上传成功之后返回的图片路径
     */
    private void returnBack(String path) {
        Intent intent = new Intent();
        intent.putExtra("result", path);
        setResult(RESULT_OK, intent);
        finish();
    }



    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case PHOTO:// 直接从相册获取
                    try {
                        startPhotoZoom(data.getData());
                    } catch (NullPointerException e) {
                        e.printStackTrace();// 用户点击取消操作
                    }
                    break;
                case CAMERA:// 调用相机拍照
                    startPhotoZoom(imagetempUri);
                    break;
                case CUTTING:// 取得裁剪后的图片
                {
                    try {
                        upload();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", false);
        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故将图片保存在Uri中，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CUTTING);
    }
}
