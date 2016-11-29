package com.melvin.share.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.allure.lbanners.LMBanners;
import com.allure.lbanners.adapter.LBaseAdapter;
import com.melvin.share.R;

/**
 * Created Time: 2016/7/21.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：加载本地图片
 */
public class LocalImgAdapter implements LBaseAdapter<Integer> {
    private Context mContext;

    public LocalImgAdapter(Context context) {
        mContext=context;
    }

    @Override
    public View getView(final LMBanners lBanners, final Context context, int position, Integer data) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.id_image);
        imageView.setImageResource(data);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }


}
