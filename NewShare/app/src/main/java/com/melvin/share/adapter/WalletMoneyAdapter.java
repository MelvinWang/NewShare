package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.WalletItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.WalletMoneyItemViewModel;

import java.util.List;


/**
 * Created Time: 2016/11/29.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：钱包金额Adapter
 */
public class WalletMoneyAdapter extends BaseAdapter<WalletMoneyAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public WalletMoneyAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WalletItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.wallet_item,
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
        final WalletItemBinding binding;

        public BindingHolder(WalletItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final User model) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new WalletMoneyItemViewModel(context, model));
            } else {
                binding.getViewModel().setEntity(model);
            }
        }
    }
}



