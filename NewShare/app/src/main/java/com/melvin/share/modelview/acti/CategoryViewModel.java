package com.melvin.share.modelview.acti;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.CategoryItemAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.order.CategoryActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2017/4/4
 * <p/>
 * 描述： 分类页面ViewModel
 */
public class CategoryViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private CategoryItemAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public CategoryViewModel(Context context, MyRecyclerView mRecyclerView) {
        super(context);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new CategoryItemAdapter(context, getData());

    }

    public void requestData(Map map) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findProductByCategory(jsonObject)
                .compose(new RxActivityHelper<CommonList<Product>>().ioMain((CategoryActivity) context, true))
                .subscribe(new RxModelSubscribe<CommonList<Product>>(context, true) {
                    @Override
                    protected void myNext(CommonList<Product> commonList) {
                        data.clear();
                        data.addAll(commonList.rows);
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
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findProductByCategory(jsonObject)
                .compose(new RxActivityHelper<CommonList<Product>>().ioMain((CategoryActivity) context, true))
                .subscribe(new RxModelSubscribe<CommonList<Product>>(context, true) {
                    @Override
                    protected void myNext(CommonList<Product> commonList) {
                        data.addAll(commonList.rows);
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

    public CategoryItemAdapter getAdapter() {
        return adapter;
    }

}
