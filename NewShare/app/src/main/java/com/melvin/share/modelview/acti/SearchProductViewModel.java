package com.melvin.share.modelview.acti;

import android.content.Context;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.ManageAddressAdapter;
import com.melvin.share.adapter.SearchProductAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.SearchProductActivity;
import com.melvin.share.ui.activity.order.CategoryActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/4
 * <p>
 * 描述： 搜索出商品来的页面ViewModel
 */
public class SearchProductViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private SearchProductAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public SearchProductViewModel(Context context, MyRecyclerView mRecyclerView ) {
        super(context);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new SearchProductAdapter(context, getData());

    }

    public void requestData(Map map) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.searchProductByKeywords(jsonObject)
                .compose(new RxActivityHelper<CommonList<Product>>().ioMain((SearchProductActivity) context, true))
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
        fromNetwork.searchProductByKeywords(jsonObject)
                .compose(new RxActivityHelper<CommonList<Product>>().ioMain((SearchProductActivity) context, true))
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

    public SearchProductAdapter getAdapter() {
        return adapter;
    }

}
