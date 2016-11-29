package com.melvin.share.databinding;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.melvin.share.R;

/**
 * Created Time: 2016/7/23.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：绑定图片
 */
public class DataBindingAdapter {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl))
            Glide.with(iv.getContext())
                    .load(imageUrl)
                    .placeholder(R.mipmap.logo)
                    .centerCrop()
                    .into(iv);
    }


}
