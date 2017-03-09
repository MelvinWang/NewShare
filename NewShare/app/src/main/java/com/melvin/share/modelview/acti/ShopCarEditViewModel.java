package com.melvin.share.modelview.acti;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.ShopCarEditAdapter;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.modelview.BaseRecyclerViewModel;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.selfcenter.ShoppingCarActivity;
import com.melvin.share.ui.activity.shopcar.ShoppingCarEditActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.RequestView;
import com.melvin.share.rx.RxSubscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/7
 * <p>
 * 描述： 购物车编辑页面ViewModel
 */
public class ShopCarEditViewModel extends BaseRecyclerViewModel<BaseModel> implements RequestView<BaseModel> {

    private ShopCarEditAdapter adapter;
    private Context context;
    private MyRecyclerView mRecyclerView;
    public List<BaseModel> data = new ArrayList<>();
    List<Product> listData = new ArrayList<>();
    private Map carMap;
    private String cartIds = "";

    public ShopCarEditViewModel(Context context, MyRecyclerView mRecyclerView ) {
        super(context);
        carMap = new HashMap();
        this.context = context;
        this.mRecyclerView = mRecyclerView;
        adapter = new ShopCarEditAdapter(context, getData());

    }

    public void requestData(Map map) {
        fromNetwork.findCartByCustomer(map)
                .compose(new RxActivityHelper<ArrayList<Product>>().ioMain((ShoppingCarEditActivity)context,true))
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
        for (Product product : listData) {
            if (product.isChecked) {
                products.add(product);
            }
        }
        if (products.size() == 0) {
            Utils.showToast(context, "至少选择一个");
        } else {
            for (int i = 0; i < products.size(); i++) {
                if (i == 0) {
                    cartIds = products.get(0).id;
                } else {
                    cartIds = cartIds + "," + products.get(i).id;
                }
            }
            carMap.put("cartIds", cartIds);


            fromNetwork.deleteCart(carMap)
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
