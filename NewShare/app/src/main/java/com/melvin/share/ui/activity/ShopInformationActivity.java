package com.melvin.share.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.AllProductAdapter;
import com.melvin.share.databinding.ActivityShopInformationBinding;
import com.melvin.share.model.BaseModel;
import com.melvin.share.model.Product;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.model.serverReturn.ShopBean;
import com.melvin.share.network.GlobalUrl;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.rx.RxSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.view.MyRecyclerView;
import com.melvin.share.view.NoRefreshRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/7/24
 * <p/>
 * 描述： 店铺信息
 */
public class ShopInformationActivity extends BaseActivity implements MyRecyclerView.LoadingListener {

    private ActivityShopInformationBinding binding;
    private Context mContext = null;
    public String imgUrl = "";
    private MyRecyclerView recyclerView;
    private ShopBean shopBean;
    private AllProductAdapter allProductAdapter;
    private List<BaseModel> data = new ArrayList<>();
    private Map map;
    private View headerView;
    private ImageView shopImg;
    private TextView shopName;
    private CheckBox collection;
    private int pageNo = 1;
    private String flag = "ALL";
    private Button allProduct;
    private Button newProduct;

    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_infomation);
        mContext = this;
        initWindow();
        initToolbar(binding.toolbar);
        ininData();
    }


    public void toSearchActivity(View v) {
        startActivity(new Intent(mContext, SearchActivity.class));
    }

    private void ininData() {
        map = new HashMap();
        recyclerView = binding.recyclerView;
        shopBean = getIntent().getParcelableExtra("shopBean");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        allProductAdapter = new AllProductAdapter(mContext, data);
        recyclerView.setAdapter(allProductAdapter);

        //头部
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = layoutInflater.inflate(R.layout.shop_title, null, false);
        shopImg = (ImageView) headerView.findViewById(R.id.shop_img);
        shopName = (TextView) headerView.findViewById(R.id.shop_name);
        collection = (CheckBox) headerView.findViewById(R.id.collection);
        allProduct = (Button) headerView.findViewById(R.id.allProduct);
        newProduct = (Button) headerView.findViewById(R.id.newProduct);

        allProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allProduct();
            }
        });
        newProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newProduct();
            }
        });

        recyclerView.addHeaderView(headerView);
        recyclerView.setLoadingListener(this);
        getShopTitel();
        allProduct();
    }

    /**
     * 获取头信息
     */
    private void getShopTitel() {
        fromNetwork.findShopById(shopBean.userId, ShapreUtils.getCustomerId())
                .compose(new RxActivityHelper<ShopBean>().ioMain(ShopInformationActivity.this, true))
                .subscribe(new RxModelSubscribe<ShopBean>(mContext, true) {
                    @Override
                    protected void myNext(ShopBean bean) {
                        shopName.setText(bean.name);
                        collection.setChecked(bean.collected);
                        String[] split = bean.picture.split("\\|");
                        if (split != null && split.length >= 1) {
                            String url = GlobalUrl.SERVICE_URL + split[0];
                            imgUrl = url;
                        }
                        Glide.with(mContext)
                                .load(imgUrl)
                                .centerCrop()
                                .into(shopImg);
                        collection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                collectUserOrDeleteUser(isChecked);
                            }
                        });
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 收藏或者取消
     *
     * @param isChecked
     */
    private void collectUserOrDeleteUser(boolean isChecked) {
        Map hashMap = new HashMap();
        hashMap.put("userId", shopBean.userId);
        ShapreUtils.putParamCustomerId(hashMap);
        if (isChecked) {
            hashMap.put("doMethod", "1");
        } else {
            hashMap.put("doMethod", "0");
        }
        LogUtils.i(hashMap.toString());

        fromNetwork.collectUserOrDeleteUser(hashMap)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(ShopInformationActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(mContext, commonReturnModel.message);

                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 所有商品
     */
    public void allProduct() {
        pageNo = 1;
        flag = "ALL";
        requestData(flag);
    }

    /**
     * 最新上架
     */
    public void newProduct() {
        pageNo = 1;
        flag = "NEW";
        requestData(flag);
    }


    /**
     * 请求网络
     */
    private void requestData(String flag) {
        map.put("userId", shopBean.userId);
        map.put("column", flag);
        map.put("pageNo", pageNo + "");
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(map)));
        LogUtils.i(jsonObject.toString());
        fromNetwork.findProductByUser(jsonObject)
                .compose(new RxActivityHelper<CommonList<Product>>().ioMain(ShopInformationActivity.this, true))
                .subscribe(new RxModelSubscribe<CommonList<Product>>(mContext, true) {
                    @Override
                    protected void myNext(CommonList<Product> commonList) {
                        if (pageNo == 1) {
                            data.clear();
                        }
                        data.addAll(commonList.rows);
                        allProductAdapter.notifyDataSetChanged();
                        recyclerView.loadMoreComplete();
                    }

                    @Override
                    protected void myError(String message) {
                        Utils.showToast(mContext, message);
                    }
                });

    }

    @Override
    public void onRefresh() {
        recyclerView.refreshComplete();
    }

    @Override
    public void onLoadMore() {
        pageNo++;
        requestData(flag);
    }
}
