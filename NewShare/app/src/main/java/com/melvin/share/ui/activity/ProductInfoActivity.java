package com.melvin.share.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.melvin.share.R;
import com.melvin.share.Utils.LogUtils;
import com.melvin.share.Utils.ShapreUtils;
import com.melvin.share.Utils.Utils;
import com.melvin.share.adapter.ProductAttriAdapter;
import com.melvin.share.adapter.ProductInfoAdapter;
import com.melvin.share.databinding.ActivityProductInfoBinding;
import com.melvin.share.model.Product;
import com.melvin.share.model.ScanProduct;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.model.serverReturn.ProductDetailBean;
import com.melvin.share.model.serverReturn.ProductStore;
import com.melvin.share.rx.RxActivityHelper;
import com.melvin.share.rx.RxModelSubscribe;
import com.melvin.share.ui.activity.common.BaseActivity;
import com.melvin.share.ui.activity.shopcar.ShoppingCarActivity;
import com.melvin.share.ui.activity.order.ConfirmOrderActivity;
import com.melvin.share.popwindow.PurchasePopupWindow;
import com.melvin.share.rx.RxSubscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/7/25
 * <p>
 * 描述：产品信息
 */
public class ProductInfoActivity extends BaseActivity {
    private ActivityProductInfoBinding binding;
    private Context mContext = null;
    private PurchasePopupWindow menuWindow;
    private boolean flag = true;//true代表购买,false代表加入购物车
    public static String productId = "";
    public static ProductDetailBean productDetail;
    private Map map;
    private Map attriMap;//请求具体库存的
    private String attributeValueIds;
    private int length;
    private List<ProductDetailBean.AttributesBean.AttributeValuesBean> data1 = new ArrayList<>();
    private List<ProductDetailBean.AttributesBean.AttributeValuesBean> data2 = new ArrayList<>();
    private List<ProductDetailBean.AttributesBean.AttributeValuesBean> data3 = new ArrayList<>();
    private List<ProductDetailBean.AttributesBean.AttributeValuesBean> data4 = new ArrayList<>();
    private ProductAttriAdapter productAttriAdapter1;
    private ProductAttriAdapter productAttriAdapter2;
    private ProductAttriAdapter productAttriAdapter3;
    private ProductAttriAdapter productAttriAdapter4;

    private String id1 = "";
    private String id2 = "";
    private String id3 = "";
    private String id4 = "";
    private int lasttPosition1 = 0;
    private int lasttPosition2 = 0;
    private int lasttPosition3 = 0;
    private int lasttPosition4 = 0;

    //加入到购物车里的参数
    private String repertoryId;
    private String repertoryProductName;
    private String repertoryProductPrice;
    private boolean scan;//true代表扫码进入
    private String scanCode;

    private boolean shopScanCodeFlag;//代表从店铺进入
    private String shopScanCode;


    @Override
    protected void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_info);
        mContext = this;
        menuWindow = new PurchasePopupWindow((Activity) mContext, itemsOnClick);
        initAdapter();
        productId = getIntent().getStringExtra("productId");
        scan = getIntent().getBooleanExtra("scan", false);
        scanCode = getIntent().getStringExtra("scanCode");

        shopScanCodeFlag = getIntent().getBooleanExtra("shopScanCodeFlag", false);
        shopScanCode = getIntent().getStringExtra("shopScanCode");

        LogUtils.i("ProductInfoActivity哈哈" + productId);
        initWindow();
        initToolbar(binding.toolbar);
        initData();
    }

    /**
     * 请求数据
     */
    private void initData() {
        attriMap = new HashMap();
        map = new HashMap();
        if (scan) {
            getDataFromScan();
        } else {
            getDataFromId();
        }

    }

    /**
     * 通过扫描获取数据
     */
    private void getDataFromScan() {
        fromNetwork.scanOrderItem(scanCode, ShapreUtils.getCustomerId())
                .compose(new RxActivityHelper<CommonReturnModel<ScanProduct>>().ioMain(ProductInfoActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel<ScanProduct>>(mContext, true) {
                    @Override
                    protected void myNext(final CommonReturnModel<ScanProduct> bean) {
                        productDetail = bean.result.product;
                        productId = productDetail.id;
                        attriMap.put("productId", productId);
                        map.put("productId", productId);
                        ShapreUtils.putParamCustomerId(map);

                        binding.collection.setChecked(productDetail.collected);
                        binding.collection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                collectProductOrDeleteProduct(isChecked);
                            }
                        });
                        binding.shopImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ShopInformationActivity.class);
                                intent.putExtra("userId", productDetail.userId);
                                startActivity(intent);
                            }
                        });
                        initTable();
                        setValueToDialog();
                    }

                    @Override
                    protected void myError(String message) {
                        LogUtils.i("哈findProductDetail");
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 通过ID获取数据
     */
    private void getDataFromId() {
        attriMap.put("productId", productId);
        map.put("productId", productId);
        ShapreUtils.putParamCustomerId(map);
        fromNetwork.findProductDetail(productId, ShapreUtils.getCustomerId())
                .compose(new RxActivityHelper<ProductDetailBean>().ioMain(ProductInfoActivity.this, true))
                .subscribe(new RxModelSubscribe<ProductDetailBean>(mContext, true) {
                    @Override
                    protected void myNext(final ProductDetailBean productDetailBean) {
                        productDetail = productDetailBean;
                        binding.collection.setChecked(productDetailBean.collected);
                        binding.collection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                collectProductOrDeleteProduct(isChecked);
                            }
                        });
                        binding.shopImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, ShopInformationActivity.class);
                                intent.putExtra("userId", productDetailBean.userId);
                                startActivity(intent);
                            }
                        });
                        initTable();
                        setValueToDialog();
                    }

                    @Override
                    protected void myError(String message) {
                        LogUtils.i("哈findProductDetail");
                        Utils.showToast(mContext, message);
                    }
                });
    }


    /**
     * 收藏或者取消
     *
     * @param isChecked
     */
    private void collectProductOrDeleteProduct(boolean isChecked) {
        Map hashMap = new HashMap();
        hashMap.put("productId", productId);
        ShapreUtils.putParamCustomerId(hashMap);
        if (isChecked) {
            hashMap.put("doMethod", "1");
        } else {
            hashMap.put("doMethod", "0");
        }
        fromNetwork.collectProductOrDeleteProduct(hashMap)
                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(ProductInfoActivity.this, true))
                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                    @Override
                    protected void myNext(CommonReturnModel commonReturnModel) {
                        Utils.showToast(mContext, commonReturnModel.message);

                    }

                    @Override
                    protected void myError(String message) {
                        LogUtils.i("哈collectProductOrDeleteProduct");
                        Utils.showToast(mContext, message);
                    }
                });
    }


    /**
     * 查询到具体商品的库存量等信息
     */
    private void findProductByAttributeValueIds() {
        attriMap.put("attributeValueIds", attributeValueIds);
        attriMap.put("length", length);
        LogUtils.i("哈findProductByAttributeValueIds" + attriMap.toString());

        fromNetwork.findProductByAttributeValueIds(attriMap)
                .compose(new RxActivityHelper<ProductStore>().ioMain(ProductInfoActivity.this, true))
                .subscribe(new RxModelSubscribe<ProductStore>(mContext, true) {
                    @Override
                    protected void myNext(ProductStore productStore) {
                        if (productStore != null) {
                            repertoryId = productStore.stockId;
                            repertoryProductName = productStore.stockName;
                            repertoryProductPrice = productStore.realPrice;
                            menuWindow.productName.setText(productStore.stockName);
                            menuWindow.price.setText("￥ " + productStore.realPrice);
                            menuWindow.store.setText("库存" + productStore.productNumber);
                        }
                    }

                    @Override
                    protected void myError(String message) {
                        LogUtils.i("哈findProductByAttributeValueIds" + "myError");
                        Utils.showToast(mContext, message);
                    }
                });
    }

    /**
     * 拼接ID
     */
    private void addId() {
        switch (length) {
            case 1:
                attributeValueIds = id1;
                break;
            case 2:
                attributeValueIds = id1 + "," + id2;
                break;
            case 3:
                attributeValueIds = id1 + "," + id2 + "," + id3;
                break;
            case 4:
                attributeValueIds = id1 + "," + id2 + "," + id3 + "," + id4;
                break;
        }
    }

    /**
     * 初始化Adapter
     */
    private void initAdapter() {
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mContext, 4);
        gridLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        menuWindow.recyclerView1.setLayoutManager(gridLayoutManager1);
        productAttriAdapter1 = new ProductAttriAdapter(mContext, data1);
        productAttriAdapter1.setOnItemClickListener(new ProductAttriAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data, int position) {
                if (lasttPosition1 != position) {
                    id1 = data;
                    lasttPosition1 = position;
                    addId();
                    findProductByAttributeValueIds();
                }
            }


        });
        menuWindow.recyclerView1.setAdapter(productAttriAdapter1);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(mContext, 4);
        gridLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        menuWindow.recyclerView2.setLayoutManager(gridLayoutManager2);
        productAttriAdapter2 = new ProductAttriAdapter(mContext, data2);
        productAttriAdapter2.setOnItemClickListener(new ProductAttriAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data, int position) {
                if (lasttPosition2 != position) {
                    id2 = data;
                    lasttPosition2 = position;
                    addId();
                    findProductByAttributeValueIds();
                }
            }
        });
        menuWindow.recyclerView2.setAdapter(productAttriAdapter2);

        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(mContext, 4);
        gridLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        menuWindow.recyclerView3.setLayoutManager(gridLayoutManager3);
        productAttriAdapter3 = new ProductAttriAdapter(mContext, data3);
        productAttriAdapter3.setOnItemClickListener(new ProductAttriAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data, int position) {
                if (lasttPosition3 != position) {
                    id3 = data;
                    lasttPosition3 = position;
                    addId();
                    findProductByAttributeValueIds();
                }
            }
        });
        menuWindow.recyclerView3.setAdapter(productAttriAdapter3);

        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(mContext, 4);
        gridLayoutManager4.setOrientation(LinearLayoutManager.VERTICAL);
        menuWindow.recyclerView4.setLayoutManager(gridLayoutManager4);
        productAttriAdapter4 = new ProductAttriAdapter(mContext, data4);
        productAttriAdapter4.setOnItemClickListener(new ProductAttriAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data, int position) {
                if (lasttPosition4 != position) {
                    id4 = data;
                    lasttPosition4 = position;
                    addId();
                    findProductByAttributeValueIds();
                }
            }
        });
        menuWindow.recyclerView4.setAdapter(productAttriAdapter4);

    }

    /**
     * 把请求到的数据放到购买的dialog里去
     */
    private void setValueToDialog() {
        if (productDetail.attributes != null) {
            int size = productDetail.attributes.size();
            length = size;
            switch (size) {
                case 1:
                    menuWindow.linearLayout1.setVisibility(View.VISIBLE);
                    menuWindow.textName1.setText(productDetail.attributes.get(0).attributeName);
                    data1.addAll(productDetail.attributes.get(0).attributeValues);
                    productAttriAdapter1.notifyDataSetChanged();
                    id1 = productDetail.attributes.get(0).attributeValues.get(0).attributeValueId + "";
                    attributeValueIds = id1;
                    findProductByAttributeValueIds();
                    break;
                case 2:
                    menuWindow.linearLayout1.setVisibility(View.VISIBLE);
                    menuWindow.linearLayout2.setVisibility(View.VISIBLE);
                    menuWindow.textName1.setText(productDetail.attributes.get(0).attributeName);
                    menuWindow.textName2.setText(productDetail.attributes.get(1).attributeName);
                    data1.addAll(productDetail.attributes.get(0).attributeValues);
                    data2.addAll(productDetail.attributes.get(1).attributeValues);
                    productAttriAdapter1.notifyDataSetChanged();
                    productAttriAdapter2.notifyDataSetChanged();

                    id1 = productDetail.attributes.get(0).attributeValues.get(0).attributeValueId + "";
                    id2 = productDetail.attributes.get(1).attributeValues.get(0).attributeValueId + "";
                    attributeValueIds = id1 + "," + id2;
                    findProductByAttributeValueIds();
                    break;
                case 3:
                    menuWindow.linearLayout1.setVisibility(View.VISIBLE);
                    menuWindow.linearLayout2.setVisibility(View.VISIBLE);
                    menuWindow.linearLayout3.setVisibility(View.VISIBLE);
                    menuWindow.textName1.setText(productDetail.attributes.get(0).attributeName);
                    menuWindow.textName2.setText(productDetail.attributes.get(1).attributeName);
                    menuWindow.textName3.setText(productDetail.attributes.get(2).attributeName);
                    data1.addAll(productDetail.attributes.get(0).attributeValues);
                    data2.addAll(productDetail.attributes.get(1).attributeValues);
                    data3.addAll(productDetail.attributes.get(2).attributeValues);
                    productAttriAdapter1.notifyDataSetChanged();
                    productAttriAdapter2.notifyDataSetChanged();
                    productAttriAdapter3.notifyDataSetChanged();

                    id1 = productDetail.attributes.get(0).attributeValues.get(0).attributeValueId + "";
                    id2 = productDetail.attributes.get(1).attributeValues.get(0).attributeValueId + "";
                    id3 = productDetail.attributes.get(2).attributeValues.get(0).attributeValueId + "";
                    attributeValueIds = id1 + "," + id2 + "," + id3;
                    findProductByAttributeValueIds();
                    break;
                case 4:
                    menuWindow.linearLayout1.setVisibility(View.VISIBLE);
                    menuWindow.linearLayout2.setVisibility(View.VISIBLE);
                    menuWindow.linearLayout3.setVisibility(View.VISIBLE);
                    menuWindow.linearLayout4.setVisibility(View.VISIBLE);
                    menuWindow.textName1.setText(productDetail.attributes.get(0).attributeName);
                    menuWindow.textName2.setText(productDetail.attributes.get(1).attributeName);
                    menuWindow.textName3.setText(productDetail.attributes.get(2).attributeName);
                    menuWindow.textName4.setText(productDetail.attributes.get(3).attributeName);
                    data1.addAll(productDetail.attributes.get(0).attributeValues);
                    data2.addAll(productDetail.attributes.get(1).attributeValues);
                    data3.addAll(productDetail.attributes.get(2).attributeValues);
                    data4.addAll(productDetail.attributes.get(3).attributeValues);
                    productAttriAdapter1.notifyDataSetChanged();
                    productAttriAdapter2.notifyDataSetChanged();
                    productAttriAdapter3.notifyDataSetChanged();
                    productAttriAdapter4.notifyDataSetChanged();

                    id1 = productDetail.attributes.get(0).attributeValues.get(0).attributeValueId + "";
                    id2 = productDetail.attributes.get(1).attributeValues.get(0).attributeValueId + "";
                    id3 = productDetail.attributes.get(2).attributeValues.get(0).attributeValueId + "";
                    id4 = productDetail.attributes.get(3).attributeValues.get(0).attributeValueId + "";
                    attributeValueIds = id1 + "," + id2 + "," + id3 + "," + id4;
                    findProductByAttributeValueIds();
                    break;
            }
        }

    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.purchase_confirm:
                    if (flag) {   //直接购买
                        List<Product> products = new ArrayList<>();
                        //构造一个商品
                        Product product = new Product();
                        product.productNumber = menuWindow.productNumber + "";
                        product.productNum = menuWindow.productNumber + "";
                        product.mainPicture = productDetail.mainPicture;
                        product.picture = productDetail.mainPicture;
                        product.productName = repertoryProductName;
                        product.repertoryName = repertoryProductName;
                        product.price = repertoryProductPrice;
                        product.postage = productDetail.postage;
                        product.stockId =repertoryId;
                        if (scan) {
                            product.scanCode =scanCode;
                        }
                        if (shopScanCodeFlag) {
                            product.scanCode =shopScanCode;
                        }
                        products.add(product);
                        Intent intent = new Intent(mContext, ConfirmOrderActivity.class);
                        intent.putParcelableArrayListExtra("products", (ArrayList<? extends Parcelable>) products);
                        intent.putExtra("fromCat",false);
                        startActivity(intent);
                    } else { //加入到购物车
                        Map carMap = new HashMap();
                        carMap.put("stockId", repertoryId);
                        carMap.put("productNumber", menuWindow.productNumber);
                        if (scan) {
                            carMap.put("scanCode", scanCode);
                        }
                        if (shopScanCodeFlag) {
                            carMap.put("scanCode", shopScanCode);
                        }
                        ShapreUtils.putParamCustomerId(carMap);
                        LogUtils.i("哈insertProductToCart" + carMap.toString());
                        JsonParser jsonParser = new JsonParser();
                        JsonObject jsonObject = (JsonObject) jsonParser.parse((new Gson().toJson(carMap)));
                        fromNetwork.insertProductToCart(jsonObject)
                                .compose(new RxActivityHelper<CommonReturnModel>().ioMain(ProductInfoActivity.this, true))
                                .subscribe(new RxSubscribe<CommonReturnModel>(mContext, true) {
                                    @Override
                                    protected void myNext(CommonReturnModel commonReturnModel) {
                                        Utils.showToast(mContext, commonReturnModel.message);
                                        ShoppingCarActivity.updateFlag = true;
                                    }

                                    @Override
                                    protected void myError(String message) {
                                        Utils.showToast(mContext, message);
                                    }
                                });
                    }
                    break;
            }

        }

    };

    /**
     * 初始化标题,绑定
     */
    private void initTable() {
        ProductInfoAdapter viewPagerAdapter = new ProductInfoAdapter(getSupportFragmentManager(), this);
        binding.viewpager.setAdapter(viewPagerAdapter);//设置适配器
        TabLayout mTabLayout = binding.tablayout;
        mTabLayout.setupWithViewPager(binding.viewpager);
    }

    /**
     * 加入购物车
     *
     * @param v
     */
    public void joinShopCar(View v) {
        flag = false;
        menuWindow.showAtLocation(binding.productInfoRoot, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 立刻购买
     *
     * @param v
     */
    public void purchaseNow(View v) {
        flag = true;
        menuWindow.showAtLocation(binding.productInfoRoot, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


}


