package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.AllProductItemBinding;
import com.melvin.share.databinding.NewProductItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.User;
import com.melvin.share.modelview.item.AllProductItemViewModel;
import com.melvin.share.modelview.item.NewProductItemViewModel;

import java.util.List;


/**
 * Created Time: 2016/7/25.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：商店里的新增商品Adapter
 */
public class NewProductAdapter extends BaseAdapter<NewProductAdapter.BindingHolder> {
    private List<BaseModel> list;
    private Context context;

    public NewProductAdapter(Context context, List<BaseModel> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewProductItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.new_product_item,
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
        final NewProductItemBinding binding;

        public BindingHolder(NewProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final Product product) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new NewProductItemViewModel(context, product));
            } else {
                binding.getViewModel().setEntity(product);
            }
        }
    }
}



