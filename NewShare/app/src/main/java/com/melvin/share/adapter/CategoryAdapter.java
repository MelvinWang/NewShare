package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.CategoryItemBinding;
import com.melvin.share.databinding.HotLabelItemBinding;
import com.melvin.share.model.CategoryBean;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.modelview.item.CategoryItemViewModel;
import com.melvin.share.modelview.item.HotLabelItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/3/31
 * <p>
 * 描述： 推荐的分类商品Adapter
 */
public class CategoryAdapter extends BaseAdapter<CategoryAdapter.BindingHolder> {
    private List< CategoryBean.MainProductsBean> list;
    private Context context;

    public CategoryAdapter(Context context, List< CategoryBean.MainProductsBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public CategoryAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CategoryItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.category_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final CategoryItemBinding binding;

        public BindingHolder(CategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final CategoryBean.MainProductsBean bean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new CategoryItemViewModel(context, bean));
            } else {
                binding.getViewModel().setEntity(bean);
            }
        }
    }

}

