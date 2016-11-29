package com.melvin.share.modelview.acti;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import com.melvin.share.adapter.ConfrimOrderAdapter;
import com.melvin.share.adapter.SearchAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.network.GlobalData;
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
 * 描述： 搜索页面ViewModel
 */
public class SearchViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {
    GridLayoutManager gridLayoutManager;
    private SearchAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public SearchViewModel(Context context, MyRecyclerView mRecyclerView, LinearLayout mRoot) {
        super(context, mRoot);
        this.context = context;
        gridLayoutManager =  new GridLayoutManager(context,4);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.mRecyclerView = mRecyclerView;
        adapter = new SearchAdapter(context, getData());

    }

    public void requestData() {
        data.addAll(GlobalData.recentSearchList);
        onRequestSuccess(data);
    }


    public void requestQueryData(Map map) {
    }

    public SearchAdapter getAdapter() {
        return adapter;
    }
    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
    }

}
