package com.melvin.share.modelview.frag;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.AllOrderFragAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxFragmentHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.fragment.main.BaseFragment;
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
 * 描述： 全部订单页面ViewModel
 */
public class AllOrderViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private AllOrderFragAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();
    public BaseFragment fragment;


    public AllOrderViewModel(Context context, BaseFragment fragment, MyRecyclerView mRecyclerView) {
        super(context);
        this.context = context;
        this.fragment = fragment;
        this.mRecyclerView = mRecyclerView;
        adapter = new AllOrderFragAdapter(context, getData());

    }

    /**
     * 第一次请求、刷新，查询
     *
     * @param map
     */
    public void requestData(Map map) {
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
                        mRecyclerView.refreshComplete();
                        Utils.showToast(context, message);
                    }
                });
    }

    /**
     刷新
     * @param map
     */
    public void requestRefresh(Map map,boolean falg) {
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findOrderByCustomer(jsonObject)
                .compose(new RxFragmentHelper<CommonList<WaitPayOrderInfo.OrderBean>>().ioMain(context, fragment, falg))
                .subscribe(new RxModelSubscribe<CommonList<WaitPayOrderInfo.OrderBean>>(context, falg) {
                    @Override
                    protected void myNext(CommonList<WaitPayOrderInfo.OrderBean> bean) {
                        data.clear();
                        data.addAll(bean.rows);
                        onRequestSuccess(data);
                        mRecyclerView.refreshComplete();
                    }

                    @Override
                    protected void myError(String message) {
                        mRecyclerView.refreshComplete();
                        Utils.showToast(context, message);
                    }
                });
    }

    /**
     * 加载更多
     *
     * @param map
     */
    public void requestQueryData(Map map) {
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));

        fromNetwork.findOrderByCustomer(jsonObject)
                .compose(new RxFragmentHelper<CommonList<WaitPayOrderInfo.OrderBean>>().ioMain(context, fragment, true))
                .subscribe(new RxModelSubscribe<CommonList<WaitPayOrderInfo.OrderBean>>(context, true) {
                    @Override
                    protected void myNext(CommonList<WaitPayOrderInfo.OrderBean> bean) {
                        data.addAll(bean.rows);
                        onRequestSuccess(data);
                        mRecyclerView.loadMoreComplete();
                    }


                    @Override
                    protected void myError(String message) {
                        mRecyclerView.loadMoreComplete();
                        Utils.showToast(context, message);
                    }
                });
    }

    public AllOrderFragAdapter getAdapter() {
        return adapter;
    }


}
