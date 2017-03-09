package com.melvin.share.modelview.acti;

import android.content.Context;

import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.ClothAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.home.ClothActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/4
 * <p/>
 * 描述： 服装页面ViewModel
 */
public class ClothViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private ClothAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();

    public ClothViewModel(Context context, MyRecyclerView mRecyclerView) {
        super(context);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new ClothAdapter(context, getData());

    }

    public void requestData(Map map) {
        fromNetwork.findProductsByCategory(map)
                .compose(new RxActivityHelper<ArrayList<Product>>().ioMain((ClothActivity)context,true))
                .subscribe(new RxModelSubscribe<ArrayList<Product>>(context, true) {
                    @Override
                    protected void myNext(ArrayList<Product> products) {
                        data.addAll(products);
                        onRequestSuccess(data);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });
    }


    public void requestQueryData(Map map) {

        fromNetwork.findProductsByCategory(map)
                .compose(new RxActivityHelper<ArrayList<Product>>().ioMain((ClothActivity)context,true))
                .subscribe(new RxModelSubscribe<ArrayList<Product>>(context, true) {
                    @Override
                    protected void myNext(ArrayList<Product> products) {
                        data.clear();
                        data.addAll(products);
                        onRequestSuccess(data);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });
    }

    public ClothAdapter getAdapter() {
        return adapter;
    }

}
