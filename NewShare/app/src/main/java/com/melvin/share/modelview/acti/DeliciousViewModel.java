package com.melvin.share.modelview.acti;

import android.content.Context;
import android.widget.LinearLayout;

import com.melvin.share.adapter.DeliciousAdapter;
import com.melvin.share.adapter.SearchProductAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Category;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;
import com.melvin.share.view.RxSubscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/4
 * <p/>
 * 描述： 餐饮页面ViewModel
 */
public class DeliciousViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private DeliciousAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public DeliciousViewModel(Context context, MyRecyclerView mRecyclerView, LinearLayout mRoot) {
        super(context, mRoot);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new DeliciousAdapter(context, getData());

    }

    public void requestData(Map map) {
        fromNetwork.findProductsByCategory(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<ArrayList<Product>>(context) {
                    @Override
                    protected void myNext(ArrayList<Product> products) {
                        data.addAll(products);
                        onRequestSuccess(data);
                    }

                    @Override
                    protected void myError(String message) {

                    }
                });

    }


    public void requestQueryData(Map map) {
        fromNetwork.findProductsByCategory(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscribe<ArrayList<Product>>(context) {
                    @Override
                    protected void myNext(ArrayList<Product> products) {
                        data.clear();
                        data.addAll(products);
                        onRequestSuccess(data);
                    }

                    @Override
                    protected void myError(String message) {

                    }
                });

    }

    public DeliciousAdapter getAdapter() {
        return adapter;
    }

}
