package com.melvin.share.modelview.item;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.serverReturn.ImgUrlBean;
import com.melvin.share.model.serverReturn.ProductDetailBean;

/**
 * Created Time: 2016/7/23.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：单个商品item的ViewModel
 */
public class ProductDetailItemViewModel extends BaseObservable {

    private ProductDetailBean.DetailsBean detailsBean;
    private Context context;

    public ProductDetailItemViewModel(Context context, ProductDetailBean.DetailsBean detailsBean) {
        this.detailsBean = detailsBean;
        this.context = context;
    }

    public void onItemClick(View view) {

    }

    public String getDetailName() {

        return detailsBean.detailName+"：";
    }
    public String getDetailValue() {
        return detailsBean.detailValue;
    }

    public void setEntity(ProductDetailBean.DetailsBean detailsBean) {
        this.detailsBean = detailsBean;
        notifyChange();
    }
}
