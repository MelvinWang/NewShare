package com.melvin.share.modelview.acti;

import android.content.Context;
import android.widget.LinearLayout;

import com.melvin.share.adapter.OrderInformationAdapter;
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
 * 描述： 待付款订单信息ViewModel
 */
public class WaitPayOrderInfoViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private OrderInformationAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public WaitPayOrderInfoViewModel(Context context, MyRecyclerView mRecyclerView, LinearLayout mRoot) {
        super(context, mRoot);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new OrderInformationAdapter(context, getData());

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

    public OrderInformationAdapter getAdapter() {
        return adapter;
    }

}
