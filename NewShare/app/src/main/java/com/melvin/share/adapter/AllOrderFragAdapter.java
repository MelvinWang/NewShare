package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.AllOrderItemBinding;
import com.melvin.share.databinding.WaitEvaluateItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.modelview.item.AllOrderItemViewModel;
import com.melvin.share.modelview.item.WaitEvaluateItemViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.melvin.share.R.id.recyclerView;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/1
 * <p>
 * 描述：全部订单页面Adapter
 */
public class AllOrderFragAdapter extends BaseAdapter<AllOrderFragAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public AllOrderFragAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AllOrderFragAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AllOrderItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.all_order_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((WaitPayOrderInfo.OrderBean) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final AllOrderItemBinding binding;

        public BindingHolder(AllOrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final WaitPayOrderInfo.OrderBean bean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new AllOrderItemViewModel(context, bean,binding.recyclerView));
            } else {
                binding.getViewModel().setEntity(bean);
            }
        }
    }

}

