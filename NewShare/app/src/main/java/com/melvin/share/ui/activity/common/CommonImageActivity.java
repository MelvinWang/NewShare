package com.melvin.share.ui.activity.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.melvin.share.R;
import com.melvin.share.view.PinchImageView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/18
 * <p/>
 * 描述：
 */
public class CommonImageActivity extends AppCompatActivity {


    private PinchImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_image);
        imageView = (PinchImageView) findViewById(R.id.pinch_image_view);
        String imageUrl = getIntent().getStringExtra("imageUrl");
        Glide.with(this)
                .load(imageUrl)

                .into(imageView);
    }
}
