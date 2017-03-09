package com.melvin.share.modelview.acti;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.ProductCollectionAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.selfcenter.ProductCollectionActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/4
 * <p>
 * 描述： 商品收藏页面ViewModel
 */
public class ProductCollectionViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private ProductCollectionAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    private Button btnDelete;
    private TextView tvEdit;
    private TextView tvcancel;
    public List<BaseModel> data = new ArrayList<>();
    List<Product> listData = new ArrayList<>();

    public ProductCollectionViewModel(Context context, MyRecyclerView mRecyclerView, TextView tvEdit, TextView tvcancel, Button btnDelete) {
        super(context);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        this.btnDelete = btnDelete;
        this.tvEdit = tvEdit;
        this.tvcancel = tvcancel;
        adapter = new ProductCollectionAdapter(context, getData());

    }

    public void requestData(Map map) {
        fromNetwork.findProductsByCustomer(map)
                .compose(new RxActivityHelper<ArrayList<Product>>().ioMain((ProductCollectionActivity)context,true))
                .subscribe(new RxModelSubscribe<ArrayList<Product>>(context, true) {
                    @Override
                    protected void myNext(ArrayList<Product> products) {
                        data.addAll(products);
                        listData.addAll(products);
                        onRequestSuccess(data);
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(context, message);
                    }
                });
    }


    public void requestQueryData(Map map) {
    }

    public ProductCollectionAdapter getAdapter() {
        return adapter;
    }


    /**
     * 编辑
     *
     * @param v
     */
    public void edit(View v) {
        tvEdit.setVisibility(View.GONE);
        tvcancel.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.VISIBLE);
        for (Product product : listData) {
            product.isShow = true;
        }
        adapter.notifyDataSetChanged();

    }

    /**
     * 取消
     *
     * @param v
     */
    public void cancel(View v) {
        tvcancel.setVisibility(View.GONE);
        tvEdit.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.GONE);
        for (Product product : listData) {
            product.isShow = false;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 删除
     *
     * @param v
     */
    public void delete(View v) {

    }

}
