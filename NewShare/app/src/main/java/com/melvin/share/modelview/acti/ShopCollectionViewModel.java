package com.melvin.share.modelview.acti;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.melvin.share.adapter.SearchProductAdapter;
import com.melvin.share.adapter.ShopCollectionAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/3
 * <p>
 * 描述： 店铺收藏页面ViewModel
 */
public class ShopCollectionViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private ShopCollectionAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    private Button btnDelete;
    private TextView tvEdit;
    private TextView tvcancel;
    public List<BaseModel> data = new ArrayList<>();
    List<User> listData = new ArrayList<>();

    public ShopCollectionViewModel(Context context, MyRecyclerView mRecyclerView, LinearLayout mRoot, TextView tvEdit,TextView tvcancel, Button btnDelete) {
        super(context, mRoot);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        this.btnDelete = btnDelete;
        this.tvEdit = tvEdit;
        this.tvcancel = tvcancel;
        adapter = new ShopCollectionAdapter(context, getData());

    }

    public void requestData() {
        List list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.password = i+"";
            user.username = i+"";
            list.add(user);
        }
        data.addAll(list);
        listData.addAll(list);
        onRequestSuccess(data);
    }


    public void requestQueryData(Map map) {
    }

    public ShopCollectionAdapter getAdapter() {
        return adapter;
    }


    /**
     * 编辑
     *
     * @param v
     */
    public void edit(View v) {
        tvEdit.setVisibility(View.GONE);
        tvcancel.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.VISIBLE);
        for (User suser : listData) {
            suser.isShow = true;
        }
        adapter.notifyDataSetChanged();

    }

    /**
     * 取消
     *
     * @param v
     */
    public void cancel(View v) {
        tvcancel.setVisibility(View.GONE);
        tvEdit.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.GONE);
        for (User suser : listData) {
            suser.isShow = false;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 删除
     *
     * @param v
     */
    public void delete(View v) {

    }

}
