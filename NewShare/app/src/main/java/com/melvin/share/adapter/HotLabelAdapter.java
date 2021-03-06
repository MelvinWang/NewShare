package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.HotLabelItemBinding;
import com.melvin.share.databinding.SearchItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.SearchBean;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.modelview.item.HotLabelItemViewModel;
import com.melvin.share.modelview.item.SearchItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/3/10
 * <p>
 * 描述： 分享热度标签Adapter
 */
public class HotLabelAdapter extends BaseAdapter<HotLabelAdapter.BindingHolder> {
    private List<HomeHotProduct.LabelsBean> list;
    private Context context;

    public HotLabelAdapter(Context context, List<HomeHotProduct.LabelsBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public HotLabelAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HotLabelItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.hot_label_item,
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
        final HotLabelItemBinding binding;

        public BindingHolder(HotLabelItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final HomeHotProduct.LabelsBean bean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new HotLabelItemViewModel(context, bean));
            } else {
                binding.getViewModel().setEntity(bean);
            }
        }
    }

}

