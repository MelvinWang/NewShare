package com.melvin.share.modelview.acti;

import android.content.Context;
import android.widget.LinearLayout;

import com.melvin.share.adapter.ManageAddressAdapter;
import com.melvin.share.adapter.SearchProductAdapter;
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
 * Data： 2016/7/25
 * <p>
 * 描述： 搜索出商品来的页面ViewModel
 */
public class SearchProductViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private SearchProductAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public SearchProductViewModel(Context context, MyRecyclerView mRecyclerView, LinearLayout mRoot) {
        super(context, mRoot);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new SearchProductAdapter(context, getData());

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

        onRequestSuccess(data);
    }


    public void requestQueryData(Map map) {
    }

    public SearchProductAdapter getAdapter() {
        return adapter;
    }

}
