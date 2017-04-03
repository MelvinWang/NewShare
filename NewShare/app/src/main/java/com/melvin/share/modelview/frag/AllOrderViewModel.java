package com.melvin.share.modelview.frag;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.AllOrderFragAdapter;
import com.melvin.share.adapter.WaitEvaluateAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.model.serverReturn.SelfInformation;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.MainActivity;
import com.melvin.share.ui.fragment.login.NormalLoginFragment;
import com.melvin.share.ui.fragment.main.HomeFragment;
import com.melvin.share.ui.fragment.order.AllOrderFragment;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.melvin.share.R.id.recyclerView;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/1
 * <p>
 * 描述： 全部订单页面ViewModel
 */
public class AllOrderViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private AllOrderFragAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();
    public AllOrderFragment fragment;


    public AllOrderViewModel(Context context, AllOrderFragment fragment,MyRecyclerView mRecyclerView) {
        super(context);
        this.context = context;
        this.fragment = fragment;
        this.mRecyclerView = mRecyclerView;
        adapter = new AllOrderFragAdapter(context, getData());

    }

    public void requestData() {
        Map map = new HashMap();
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));

        fromNetwork.findOrderByCustomer(jsonObject)
                .compose(new RxFragmentHelper<CommonList<WaitPayOrderInfo.OrderBean>>().ioMain(context, fragment, true))
                .subscribe(new RxModelSubscribe<CommonList<WaitPayOrderInfo.OrderBean>>(context, true) {
                    @Override
                    protected void myNext(CommonList<WaitPayOrderInfo.OrderBean> bean) {
                        data.clear();
                        data.addAll(bean.rows);
                        onRequestSuccess(data);
                        mRecyclerView.refreshComplete();
                    }


                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });
    }


    public void requestQueryData(Map map) {
    }

    public AllOrderFragAdapter getAdapter() {
        return adapter;
    }


}
