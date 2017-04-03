package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.HotLabelItemBinding;
import com.melvin.share.databinding.SingleOrderItemBinding;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.modelview.item.HotLabelItemViewModel;
import com.melvin.share.modelview.item.SingleOrderItemViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/1
 * <p>
 * 描述： 全部订单单个商品页面Adapter
 */
public class SingleOrderAdapter extends BaseAdapter<SingleOrderAdapter.BindingHolder> {
    private List<WaitPayOrderInfo.OrderBean.OrderItemResponsesBean> list=new ArrayList<>();
    private Context context;

    public SingleOrderAdapter(Context context, List<WaitPayOrderInfo.OrderBean.OrderItemResponsesBean> beanList) {
        this.list = beanList;
        this.context = context;
    }

    @Override
    public SingleOrderAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SingleOrderItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.single_order_item,
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
        final SingleOrderItemBinding binding;

        public BindingHolder(SingleOrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new SingleOrderItemViewModel(context, bean));
            } else {
                binding.getViewModel().setEntity(bean);
            }
        }
    }

}

