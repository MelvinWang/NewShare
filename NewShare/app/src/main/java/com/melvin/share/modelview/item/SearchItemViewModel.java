package com.melvin.share.modelview.item;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.melvin.share.model.SearchBean;
import com.melvin.share.model.User;
import com.melvin.share.ui.activity.SearchProductActivity;

/**
 * Created Time: 2016/8/3.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：搜索item的ViewModel
 */
public class SearchItemViewModel extends BaseObservable {

    private SearchBean searchBean;
    private Context context;

    public SearchItemViewModel(Context context, SearchBean searchBean) {
        this.searchBean = searchBean;
        this.context = context;
    }

    public void onItemClick(View view) {
        Intent intent = new Intent(context, SearchProductActivity.class);
        intent.putExtra("keyWord", searchBean.keyWord);
        context.startActivity(intent);
    }

    public String getSearchWord() {
        return searchBean.keyWord;
    }

    public void setEntity(SearchBean searchBean) {
        this.searchBean = searchBean;
        notifyChange();
    }
}
