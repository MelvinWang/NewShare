package com.melvin.share.modelview.acti;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.DepositRecordAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.model.serverReturn.DepositRecordBean;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.selfcenter.DepositRecordActivity;
import com.melvin.share.ui.activity.selfcenter.ManageAddressActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data：2017/7/4
 * <p/>
 * 描述： 提现记录ViewModel
 */
public class DepositRecordViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private DepositRecordAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public DepositRecordViewModel(Context context, MyRecyclerView mRecyclerView) {
        super(context);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new DepositRecordAdapter(context, getData());
    }


    /**
     * 第一次请求或者刷新
     *
     * @param
     */
    public void requestData(Map map) {
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findWithdrawCashByCustomer(jsonObject)
                .compose(new RxActivityHelper<CommonList<DepositRecordBean>>().ioMain((DepositRecordActivity) context, true))
                .subscribe(new RxModelSubscribe<CommonList<DepositRecordBean>>(context, true) {
                    @Override
                    protected void myNext(CommonList<DepositRecordBean> list) {
                        data.clear();
                        data.addAll(list.rows);
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
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findWithdrawCashByCustomer(jsonObject)
                .compose(new RxActivityHelper<CommonList<DepositRecordBean>>().ioMain((DepositRecordActivity) context, true))
                .subscribe(new RxModelSubscribe<CommonList<DepositRecordBean>>(context, true) {
                    @Override
                    protected void myNext(CommonList<DepositRecordBean> list) {
                        data.addAll(list.rows);
                        onRequestSuccess(data);
                        mRecyclerView.loadMoreComplete();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });

    }
    

    public DepositRecordAdapter getAdapter() {
        return adapter;
    }


}
