package com.melvin.share.databinding;

import android.databinding.BindingAdapter;

import com.melvin.share.adapter.BaseAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.view.MyRecyclerView;

import java.util.List;


/**
 * Created Time: 2016/7/17.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：绑定
 */
public class DBRecyclerView {
    public static final String TAG = "DBRecyclerView";

    @BindingAdapter({"adapter"})
    public static void bindAdapter(MyRecyclerView recyclerView, BaseAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"data"})
    public static void bindData(MyRecyclerView recyclerView, List<BaseModel> baseModels) {
        recyclerView.notifyDataSetChanged();
    }


}
