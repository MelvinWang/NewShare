package com.melvin.share.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.melvin.share.R;
import com.melvin.share.databinding.ProductInfoimgItemBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.User;
import com.melvin.share.model.serverReturn.ImgUrlBean;
import com.melvin.share.modelview.item.ProductInfoimgItemViewModel;

import java.util.List;


/**
 * Created Time: 2017/4/9.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：单个商品详细信息底部图片Adapter
 */
public class ProductInformationAdapter extends BaseAdapter<ProductInformationAdapter.BindingHolder> {
    private List<String> list;
    private Context context;

    public ProductInformationAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProductInfoimgItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.product_infoimg_item,
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
        final ProductInfoimgItemBinding binding;

        public BindingHolder(ProductInfoimgItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindObject(final String url) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ProductInfoimgItemViewModel(context, url));
            } else {
                binding.getViewModel().setEntity(url);
            }
        }
    }
}



