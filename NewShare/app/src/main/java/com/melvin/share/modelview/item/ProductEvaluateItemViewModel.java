package com.melvin.share.modelview.item;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.Utils.DateUtil;
import com.melvin.share.model.Evaluation;
import com.melvin.share.model.User;
import com.melvin.share.view.RatingBar;

/**
 * Created Time: 2016/7/23.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：单个商品信息的评价item的ViewModel
 */
public class ProductEvaluateItemViewModel extends BaseObservable {

    private Evaluation bean;
    private Context context;
    private RatingBar ratingBar;

    public ProductEvaluateItemViewModel(Context context, Evaluation bean,RatingBar ratingBar) {
        this.bean = bean;
        this.context = context;
        this.ratingBar = ratingBar;
        ratingBar.setStar(bean.startlevel);
    }

    public void onItemClick(View view) {

    }

    public String getName() {
        return  bean.username;
    }

    public String getContent() {
        return  bean.content;
    }
    public String getProductName() {
        return  bean.productName;
    }
    public String getTime() {
        return DateUtil.getDateString(bean.evaluateDate);
    }
    public void setEntity(Evaluation bean,RatingBar ratingBar) {
        this.bean = bean;
        this.ratingBar = ratingBar;
        ratingBar.setStar(bean.startlevel);
        notifyChange();
    }
}
