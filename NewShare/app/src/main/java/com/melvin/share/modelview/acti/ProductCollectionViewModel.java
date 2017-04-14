package com.melvin.share.modelview.acti;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.ProductCollectionAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.selfcenter.ProductCollectionActivity;
import com.melvin.share.ui.activity.selfcenter.ShopCollectionActivity;
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
    /**
     * 第一次请求或者刷新
     *
     * @param map
     */
    public void requestData(Map map) {
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findCollectProduct(jsonObject)
                .compose(new RxActivityHelper<CommonList<Product>>().ioMain((ProductCollectionActivity) context, true))
                .subscribe(new RxModelSubscribe<CommonList<Product>>(context, true) {
                    @Override
                    protected void myNext(CommonList<Product> bean) {
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

    /**
     * 加载更多
     *
     * @param map
     */
    public void requestQueryData(Map map) {
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findCollectProduct(jsonObject)
                .compose(new RxActivityHelper<CommonList<Product>>().ioMain((ProductCollectionActivity) context, true))
                .subscribe(new RxModelSubscribe<CommonList<Product>>(context, true) {
                    @Override
                    protected void myNext(CommonList<Product> bean) {
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

        final List<Product> beans = new ArrayList<>();//选中的商品
        List<String> ids = new ArrayList<>();//选中的商品的ID
        for (Product bean : listData) {
            if (bean.isChecked) {
                beans.add(bean);
            }
        }
        if (beans.size() == 0) {
            Utils.showToast(context, "至少选择一个");
        } else {
            for (Product bean : beans) {
                ids.add(bean.collectId);
            }

            String[] arr = ids.toArray(new String[ids.size()]);
            fromNetwork.deleteCollectByIds(arr)
                    .compose(new RxActivityHelper<CommonReturnModel>().ioMain((ProductCollectionActivity) context, true))
                    .subscribe(new RxSubscribe<CommonReturnModel>(context, true) {
                        @Override
                        protected void myNext(CommonReturnModel commonReturnModel) {
                            data.removeAll(beans);
                            onRequestSuccess(data);
                            Utils.showToast(context, commonReturnModel.message);
                        }

                        @Override
                        protected void myError(String message) {
                            Utils.showToast(context, message);
                        }
                    });
        }
    }

}
