package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.SearchItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.SearchBean;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.SearchItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/3
 * <p>
 * 描述： 搜索Adapter
 */
public class SearchAdapter extends BaseAdapter<SearchAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public SearchAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SearchAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.search_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((SearchBean) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final SearchItemBinding binding;

        public BindingHolder(SearchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final SearchBean searchBean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new SearchItemViewModel(context, searchBean));
            } else {
                binding.getViewModel().setEntity(searchBean);
            }
        }
    }

}

