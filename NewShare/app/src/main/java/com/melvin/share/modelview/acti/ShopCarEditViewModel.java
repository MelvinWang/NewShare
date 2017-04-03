package com.melvin.share.modelview.acti;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.ShopCarEditAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.shopcar.ShoppingCarActivity;
import com.melvin.share.ui.activity.shopcar.ShoppingCarEditActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;
import com.melvin.share.rx.RxSubscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/3/31
 * <p>
 * 描述： 购物车编辑页面ViewModel
 */
public class ShopCarEditViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private ShopCarEditAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();
    List<Product> listData = new ArrayList<>();
    private String cartIds = "";

    public ShopCarEditViewModel(Context context, MyRecyclerView mRecyclerView ) {
        super(context);
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new ShopCarEditAdapter(context, getData());

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
        fromNetwork.findCartByCustomer(jsonObject)
                .compose(new RxActivityHelper<CommonList<Product>>().ioMain((ShoppingCarEditActivity)context,true))
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


    public void requestQueryData(Map map) {
        ShapreUtils.putParamCustomerId(map);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        fromNetwork.findCartByCustomer(jsonObject)
                .compose(new RxActivityHelper<CommonList<Product>>().ioMain((ShoppingCarEditActivity)context,true))
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


    public ShopCarEditAdapter getAdapter() {
        return adapter;
    }

    /**
     * 全选
     *
     * @return
     */
    public CompoundButton.OnCheckedChangeListener getCheckAllListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (Product product : listData) {
                    product.isChecked = isChecked;
                }
                adapter.notifyDataSetChanged();

            }
        };
    }

    /**
     * 删除
     *
     * @param view
     */
    public void onClickDelete(View view) {
        final List<Product> products = new ArrayList<>();//选中的商品
        List<String> ids = new ArrayList<>();//选中的商店的ID
        for (Product product : listData) {
            if (product.isChecked) {
                products.add(product);
            }
        }
        if (products.size() == 0) {
            Utils.showToast(context, "至少选择一个");
        } else {
            for (Product bean : products) {
                ids.add(bean.id);
            }
            String[] arr = ids.toArray(new String[ids.size()]);

            fromNetwork.deleteCartByIds(arr)
                    .compose(new RxActivityHelper<CommonReturnModel>().ioMain((ShoppingCarEditActivity)context,true))
                    .subscribe(new RxSubscribe<CommonReturnModel>(context, true) {
                        @Override
                        protected void myNext(CommonReturnModel commonReturnModel) {
                            Utils.showToast(context, commonReturnModel.message);
                            data.removeAll(products);
                            onRequestSuccess(data);
                            ShoppingCarActivity.updateFlag = true;
                        }

                        @Override
                        protected void myError(String message) {
                            Utils.showToast(context, message);
                        }
                    });
        }
    }
}
