package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;
import android.widget.CompoundButton;

import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.RxCarBus;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.event.PostEvent;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.BaseReturnModel;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.network.NetworkUtil;
import com.melvin.share.ui.activity.ProductInfoActivity;
import com.melvin.share.view.RxSubscribe;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import retrofit.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created Time: 2016/7/23.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：购物车item的ViewModel
 */
public class ShopCarItemViewModel extends BaseObservable {
    private Retrofit retrofit;
    protected NetworkUtil.FromNetwork fromNetwork;
    private Product product;
    private Context context;
    private boolean isShowEdit = true;
    private boolean isShowDone = false;
    private String number;
    private String lastNumber;

    public ShopCarItemViewModel(Context context, Product product) {
        retrofit = NetworkUtil.getRetrofit();
        fromNetwork = retrofit.create(NetworkUtil.FromNetwork.class);
        this.product = product;
        number = product.productNumber;
        lastNumber = product.productNumber;
        this.context = context;
    }

    public void onItemClick(View view) {
        context.startActivity(new Intent(context, ProductInfoActivity.class));
    }

    public String getProductName() {
        return product.repertoryName;
    }

    public String getPrice() {
        return "￥" + product.price;
    }

    public String getNumber() {
        return number;
    }

    public boolean getIsChecked() {
        return product.isChecked;
    }

    public void setIsChecked(boolean b) {
        product.isChecked = b;
    }


    public void onAddClick(View view) {
        this.number = Integer.parseInt(number) + 1 + "";
        notifyChange();

    }

    public void onDeleteClick(View view) {
        this.number = ((Integer.parseInt(number) - 1) == 0) ? "0" : (Integer.parseInt(number) - 1 + "");
        notifyChange();
    }


    public boolean getIsShowEdit() {
        return isShowEdit;
    }

    public void setIsShowEdit(boolean b) {
        this.isShowEdit = b;
    }

    public boolean getIsShowDone() {
        return isShowDone;
    }

    public void setIsShowDone(boolean b) {
        this.isShowDone = b;
    }

    public void edit(View view) {
        setIsShowEdit(false);
        setIsShowDone(true);
        notifyChange();
    }

    public void done(View view) {
        setIsShowEdit(true);
        setIsShowDone(false);
        notifyChange();
        if (!number.equals(lastNumber)) {//需要修改
            //加入到购物车
            Map carMap = new HashMap();
            carMap.put("repertory.id", product.repertoryId);
            carMap.put("productNum", number);
            ShapreUtils.putParamCustomerDotId(carMap);
            fromNetwork.persist(carMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RxSubscribe<BaseReturnModel>(context) {
                        @Override
                        protected void myNext(BaseReturnModel baseReturnModel) {
                            Utils.showToast(context, baseReturnModel.message);
                        }

                        @Override
                        protected void myError(String message) {

                        }
                    });
        }
    }

    public void oncheckProduct(View view) {
        setIsChecked(!product.isChecked);
        BigDecimal multiply = new BigDecimal(product.price).multiply(new BigDecimal(number));
        PostEvent postEvent = new PostEvent();
        postEvent.flag = product.isChecked;
        postEvent.price = multiply;
        RxCarBus.get().post(postEvent);
        notifyChange();
    }


    public String getImgUrl() {
        String[] split = product.picture.split("\\|");
        if (split != null && split.length >= 1) {
            String url = GlobalUrl.SERVICE_URL + split[0];
            LogUtils.e("哈哈" + url);
            return url;
        }
        return "";
    }

    public void setEntity(Product product) {
        this.product = product;
        notifyChange();
    }
}
