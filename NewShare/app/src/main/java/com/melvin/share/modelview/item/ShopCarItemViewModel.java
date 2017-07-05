package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.rx.RxCarBus;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.event.PostEvent;
import com.melvin.share.model.Product;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.network.NetworkUtil;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.ProductInfoActivity;
import com.melvin.share.ui.activity.shopcar.ShoppingCarActivity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;


/**
 * Created Time: 2017/3/31.
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
        number = product.productNum;
        lastNumber = product.productNum;
        this.context = context;
    }

    public void onItemClick(View view) {
        context.startActivity(new Intent(context, ProductInfoActivity.class));
    }

    public String getProductName() {
        return product.productName;
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

    public boolean getIsShowProductCode() {
        if (TextUtils.isEmpty(product.scanCode)) {//为空不显示
            return false;
        } else if (product.scanCode.split("_").length>=2) {//是店铺码
            return false;
        } else {//是商品码
            return true;
        }
    }

    public boolean getIsShowShopCode() {
        if (TextUtils.isEmpty(product.scanCode)) {//为空不显示
            return false;
        } else if (product.scanCode.split("_").length>=2) {//是店铺码
            return true;
        } else {//是商品码
            return false;
        }
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
            carMap.put("stockId", product.stockId);
            carMap.put("productNumber", number);
            ShapreUtils.putParamCustomerId(carMap);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(carMap)));
            fromNetwork.updateCartProduct(jsonObject)
                    .compose(new RxActivityHelper<CommonReturnModel>().ioMain((ShoppingCarActivity) context, true))
                    .subscribe(new RxSubscribe<CommonReturnModel>(context, true) {
                        @Override
                        protected void myNext(CommonReturnModel commonReturnModel) {
                            Utils.showToast(context, commonReturnModel.message);
                        }

                        @Override
                        protected void myError(String message) {
                            Utils.showToast(context, message);
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
        String[] split = product.mainPicture.split("\\|");
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
