package com.melvin.share.modelview.acti;

import android.content.Context;
import android.widget.LinearLayout;

import com.melvin.share.adapter.LogisticsInfoAdapter;
import com.melvin.share.adapter.ManageAddressAdapter;
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
 * Data： 2016/8/7
 * <p>
 * 描述： 查看物流页面ViewModel
 */
public class LogisticsInfoViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private LogisticsInfoAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public LogisticsInfoViewModel(Context context, MyRecyclerView mRecyclerView, LinearLayout mRoot) {
        super(context, mRoot);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new LogisticsInfoAdapter(context, getData());

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

    public LogisticsInfoAdapter getAdapter() {
        return adapter;
    }

}
