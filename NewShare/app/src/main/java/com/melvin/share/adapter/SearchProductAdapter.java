package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.ReceiveAddressItemBinding;
import com.melvin.share.databinding.SearchProductItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.ManageAddressItemViewModel;
import com.melvin.share.modelview.item.SearchProductItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/7/25
 * <p>
 * 描述： 搜索出商品来的页面Adapter
 */
public class SearchProductAdapter extends BaseAdapter<SearchProductAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public SearchProductAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SearchProductAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchProductItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.search_product_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((User) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final SearchProductItemBinding binding;

        public BindingHolder(SearchProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final User user) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new SearchProductItemViewModel(context, user));
            } else {
                binding.getViewModel().setEntity(user);
            }
        }
    }

}

