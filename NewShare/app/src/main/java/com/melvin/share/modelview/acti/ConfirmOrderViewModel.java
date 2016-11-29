package com.melvin.share.modelview.acti;

import android.content.Context;
import android.widget.LinearLayout;

import com.melvin.share.adapter.ConfrimOrderAdapter;
import com.melvin.share.adapter.ManageAddressAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
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
 * 描述： 确认订单ViewModel
 */
public class ConfirmOrderViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private ConfrimOrderAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public ConfirmOrderViewModel(Context context, MyRecyclerView mRecyclerView, LinearLayout mRoot) {
        super(context, mRoot);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new ConfrimOrderAdapter(context, getData());
    }

    public void requestData(ArrayList<Product> products) {
        data.addAll(products);
        onRequestSuccess(data);
    }


    public void requestQueryData(Map map) {
    }

    public ConfrimOrderAdapter getAdapter() {
        return adapter;
    }

}
