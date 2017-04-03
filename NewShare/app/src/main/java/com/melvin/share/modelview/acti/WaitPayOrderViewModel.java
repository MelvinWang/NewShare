package com.melvin.share.modelview.acti;

import android.content.Context;

import com.melvin.share.adapter.ConfrimOrderAdapter;
import com.melvin.share.adapter.WaitpayOrderAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
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
 * Data： 2017/4/1
 * <p>
 * 描述： 订单信息 未付款 ViewModel
 */
public class WaitPayOrderViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private WaitpayOrderAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public WaitPayOrderViewModel(Context context, MyRecyclerView mRecyclerView) {
        super(context);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new WaitpayOrderAdapter(context, getData());
    }

    public void requestData(ArrayList<WaitPayOrderInfo.OrderBean.OrderItemResponsesBean> list) {
        data.addAll(list);
        onRequestSuccess(data);
    }


    public void requestQueryData(Map map) {
    }

    public WaitpayOrderAdapter getAdapter() {
        return adapter;
    }

}
