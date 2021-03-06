package com.melvin.share.modelview.acti;

import android.content.Context;
import android.widget.LinearLayout;

import com.melvin.share.adapter.OrderInformationAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.model.WaitPayOrderInfo;
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
 * 描述： 待发货订单信息ViewModel
 */
public class WaitSendProOrderInfoViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private OrderInformationAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public WaitSendProOrderInfoViewModel(Context context, MyRecyclerView mRecyclerView ) {
        super(context);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new OrderInformationAdapter(context, getData());

    }

    public void requestData(ArrayList<WaitPayOrderInfo.OrderBean.OrderItemResponsesBean> list) {
        data.addAll(list);
        onRequestSuccess(data);
    }


    public void requestQueryData(Map map) {
    }

    public OrderInformationAdapter getAdapter() {
        return adapter;
    }

}
