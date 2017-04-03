package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.ConfirmOrderItemBinding;
import com.melvin.share.databinding.WaitpayOrderItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.modelview.item.ConfirmOrderItemViewModel;
import com.melvin.share.modelview.item.WaitpayOrderItemViewModel;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2017/4/1
 * <p>
 * 描述： 订单信息 未付款Adapter
 */
public class WaitpayOrderAdapter extends BaseAdapter<WaitpayOrderAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public WaitpayOrderAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WaitpayOrderItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.waitpay_order_item,
                parent,
                false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindObject((WaitPayOrderInfo.OrderBean.OrderItemResponsesBean) list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Holder
     */
    public class BindingHolder extends RecyclerView.ViewHolder {
        final WaitpayOrderItemBinding binding;

        public BindingHolder(WaitpayOrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final WaitPayOrderInfo.OrderBean.OrderItemResponsesBean bean) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new WaitpayOrderItemViewModel(context, bean));
            } else {
                binding.getViewModel().setEntity(bean);
            }
        }
    }

}

