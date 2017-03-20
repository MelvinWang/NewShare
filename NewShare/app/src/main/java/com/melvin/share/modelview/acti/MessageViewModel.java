package com.melvin.share.modelview.acti;

import android.content.Context;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.MessageAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.MessageInfo;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.selfcenter.MessageActivity;
import com.melvin.share.ui.activity.selfcenter.ProductCollectionActivity;
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
 * 描述： 消息通知ViewModel
 */
public class MessageViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private MessageAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();
    List<MessageInfo> listData = new ArrayList<>();

    public MessageViewModel(Context context, MyRecyclerView mRecyclerView) {
        super(context);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new MessageAdapter(context, getData());

    }

    /**
     * 第一次请求或者刷新
     *
     * @param map
     */
    public void requestData(Map map) {
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findNewsByCustomerId(jsonObject)
                .compose(new RxActivityHelper<CommonList<MessageInfo>>().ioMain((MessageActivity) context, true))
                .subscribe(new RxModelSubscribe<CommonList<MessageInfo>>(context, true) {
                    @Override
                    protected void myNext(CommonList<MessageInfo> bean) {
                        data.clear();
                        listData.clear();
                        data.addAll(bean.rows);
                        listData.addAll(bean.rows);
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


    public void requestQueryData(Map map) {
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findNewsByCustomerId(jsonObject)
                .compose(new RxActivityHelper<CommonList<MessageInfo>>().ioMain((MessageActivity) context, true))
                .subscribe(new RxModelSubscribe<CommonList<MessageInfo>>(context, true) {
                    @Override
                    protected void myNext(CommonList<MessageInfo> bean) {
                        data.addAll(bean.rows);
                        listData.addAll(bean.rows);
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

    public MessageAdapter getAdapter() {
        return adapter;
    }

}
