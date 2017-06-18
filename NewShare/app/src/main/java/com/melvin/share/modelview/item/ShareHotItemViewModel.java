package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.adapter.HotLabelAdapter;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.ProductInfoActivity;

/**
 * Created Time: 2016/7/31.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：分享热度item的ViewModel
 */
public class ShareHotItemViewModel extends BaseObservable {

    private HomeHotProduct model;
    private Context context;
    private RecyclerView recyclerView;

    public ShareHotItemViewModel(Context context, HomeHotProduct model, RecyclerView recyclerView) {
        this.model = model;
        this.context = context;
        this.recyclerView = recyclerView;
        setRecyclerData();
    }

    private void setRecyclerData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        HotLabelAdapter   adpter = new HotLabelAdapter(context, model.labels);
        recyclerView.setAdapter(adpter);
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(context, ProductInfoActivity.class);
        intent.putExtra("productId", model.id);
        context.startActivity(intent);

    }

    public String getName() {

        return model.productName;
    }

    public String getShareNumber() {

        return model.shareTimes;
    }

    public String getImgUrl() {
        String[] split = model.picture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.PICTURE_URL + split[0];
            LogUtils.i(url);
            return url;
        }
        return "";
    }

    public void setEntity(HomeHotProduct model) {
        this.model = model;
        setRecyclerData();
        notifyChange();
    }
}
