package com.melvin.share.ui.fragment.productinfo;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allure.lbanners.LMBanners;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ViewUtils;
import com.melvin.share.adapter.ProductDetailAdapter;
import com.melvin.share.adapter.ProductInformationAdapter;
import com.melvin.share.adapter.UrlImgAdapter;
import com.melvin.share.databinding.FragmentProductinfoBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.serverReturn.ImgUrlBean;
import com.melvin.share.model.serverReturn.ProductDetailBean;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.ui.activity.ProductInfoActivity;
import com.melvin.share.ui.fragment.main.BaseFragment;
import com.melvin.share.view.ScrollRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.melvin.share.R.id.recyclerView;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/25
 * <p/>
 * 描述：单个商品详细信息
 */
public class ProductInfoFragment extends BaseFragment {
    private FragmentProductinfoBinding binding;
    private Context mContext;
    private LMBanners mLBanners;
    private RecyclerView mRecyclerView;
    private RecyclerView mDetailRecyclerView;
    private ProductInformationAdapter productInformationAdapter;
    private ProductDetailAdapter productDetailAdapter;
    private List<String> data = new ArrayList<>();
    private List<BaseModel> datadetail = new ArrayList<>();
    private List<ImgUrlBean> ImgUrlBeanList = new ArrayList<>();
    //网络图片
    private List<String> networkImages = new ArrayList<>();

    public ProductDetailBean productDetailBean;
    private View root;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_productinfo, container, false);
        if (root == null) {
            mContext = getActivity();
            mLBanners = binding.banners;
            initData();
            initAdapter();
            root = binding.getRoot();
            requestData();
        } else {
            ViewUtils.removeParent(root);
        }
        return root;

    }

    /**
     * 初始化数据
     */
    private void initData() {
        productDetailBean = ProductInfoActivity.productDetail;
        mRecyclerView = binding.recyclerView;
        mDetailRecyclerView = binding.detailRecyclerView;

    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        productInformationAdapter = new ProductInformationAdapter(mContext, data);
        mRecyclerView.setAdapter(productInformationAdapter);

        LinearLayoutManager linearLayoutDetailManager = new LinearLayoutManager(mContext);
        linearLayoutDetailManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDetailRecyclerView.setLayoutManager(linearLayoutDetailManager);
        productDetailAdapter = new ProductDetailAdapter(mContext, datadetail);
        mDetailRecyclerView.setAdapter(productDetailAdapter);
    }

    /**
     * 数据赋值
     */
    private void requestData() {
        String picture = productDetailBean.mainPicture;
        if (!TextUtils.isEmpty(picture)) {
            String[] split = picture.split("\\|");
            if (split != null) {
                for (int i = 0; i < split.length; i++) {
                    networkImages.add(GlobalUrl.SERVICE_URL + split[i]);
                    ImgUrlBean ImgUrlBean = new ImgUrlBean(GlobalUrl.SERVICE_URL + split[i]);
                    ImgUrlBeanList.add(ImgUrlBean);
                }
            }
        }
        binding.productName.setText(productDetailBean.name);
        binding.shareTime.setText(productDetailBean.shareTimes + "分享");
        binding.price.setText("￥ " + productDetailBean.price);
        binding.postage.setText("邮费:" +productDetailBean.postage);
        binding.place.setText(productDetailBean.place);

        //Banner图片
        mLBanners.setAdapter(new UrlImgAdapter(mContext), networkImages);

        //参数信息
        datadetail.addAll(productDetailBean.properties);
        productDetailAdapter.notifyDataSetChanged();


        //底部图片
        if (!TextUtils.isEmpty(productDetailBean.otherPicture)){
            String[] split = productDetailBean.otherPicture.split("\\|");
            List<String> splitList = new ArrayList<String>();
            Collections.addAll(splitList, split);
            for (String s:splitList) {
                LogUtils.i("底部图片URL"+s);
            }

            data.addAll(splitList);
            productInformationAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mLBanners.stopImageTimerTask();
    }

    @Override
    public void onResume() {
        super.onResume();
        mLBanners.startImageTimerTask();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLBanners.clearImageTimerTask();
    }
}