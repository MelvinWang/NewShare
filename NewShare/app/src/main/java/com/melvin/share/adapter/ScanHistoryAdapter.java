package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.ProductCollectionItemBinding;
import com.melvin.share.databinding.ScanHistoryItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.ProductCollectionItemViewModel;
import com.melvin.share.modelview.item.ScanHistoryItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/4
 * <p>
 * 描述：浏览记录页面Adapter
 */
public class ScanHistoryAdapter extends BaseAdapter<ScanHistoryAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public ScanHistoryAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ScanHistoryAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ScanHistoryItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.scan_history_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((Product) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final ScanHistoryItemBinding binding;

        public BindingHolder(ScanHistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final Product product) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ScanHistoryItemViewModel(context, product));
            } else {
                binding.getViewModel().setEntity(product);
            }
        }
    }

}

