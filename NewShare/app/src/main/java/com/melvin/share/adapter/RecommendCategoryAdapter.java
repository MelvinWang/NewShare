package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.RecommendCategoryItemBinding;
import com.melvin.share.databinding.RecommendShopItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.CategoryBean;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.modelview.item.HomeShopItemViewModel;
import com.melvin.share.modelview.item.RecommendCategoryItemViewModel;

import java.util.List;


/**
 * Created Time: 2017/3/31.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：推荐分类商品的Adapter
 */
public class RecommendCategoryAdapter extends BaseAdapter<RecommendCategoryAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public RecommendCategoryAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecommendCategoryItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.recommend_category_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((CategoryBean) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final RecommendCategoryItemBinding binding;

        public BindingHolder(RecommendCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final CategoryBean bean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new RecommendCategoryItemViewModel(context, bean,binding.recyclerView));
            } else {
                binding.getViewModel().setEntity(bean);
            }
        }
    }
}



