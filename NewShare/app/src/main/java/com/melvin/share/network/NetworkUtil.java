package com.melvin.share.network;


import com.melvin.share.model.Category;
import com.melvin.share.model.Product;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.model.serverReturn.BaseReturnModel;
import com.melvin.share.model.serverReturn.ProductDetailBean;
import com.melvin.share.model.serverReturn.ProductStore;
import com.melvin.share.model.serverReturn.SelfInformation;
import com.melvin.share.model.serverReturn.ShopBean;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;
import java.util.Map;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import rx.Observable;

/**
 * Created Time: 2016/7/17.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：网络请求工具及其接口
 */
public class NetworkUtil {
    //总请求路径
    public static final String API_URL = GlobalUrl.SERVICE_URL;
    private static Retrofit sRetrofit;

    //定义接口
    public interface FromNetwork {
        //首页分类
        @POST("/app/category/findAll")
        Observable<ArrayList<Category>> categoryFind();

        //首页推荐
        @FormUrlEncoded
        @POST("/app/product/findProductsInHomePage")
        Observable<ArrayList<Product>> findProductsInHomePage(@FieldMap Map<Object, Object> map);

        //首页推荐商家
        @POST("/app/seller/findRecommendedSeller")
        Observable<ArrayList<ShopBean>> findRecommendedSeller();

        //查看分类下的商品
        @FormUrlEncoded
        @POST("/app/product/findProductsByCategory")
        Observable<ArrayList<Product>> findProductsByCategory(@FieldMap Map<Object, Object> map);

        //会员注册验证接口
        @FormUrlEncoded
        @POST("/app/customer/checkCustomer")
        Observable<BaseReturnModel> checkCustomer(@FieldMap Map<Object, Object> map);

        //短信验证码发送接口
        @FormUrlEncoded
        @POST("/common/sendMessage")
        Observable<BaseReturnModel> sendMessage(@FieldMap Map<Object, Object> map);

        //会员注册接口
        @FormUrlEncoded
        @POST("/app/customer/regist")
        Observable<BaseReturnModel> regist(@FieldMap Map<Object, Object> map);

        //会员凭手机验证码登录接口
        @FormUrlEncoded
        @POST("/app/customer/loginByCode")
        Observable<BaseReturnModel<SelfInformation>> loginByCode(@FieldMap Map<Object, Object> map);

        //查看商品详情
        @FormUrlEncoded
        @POST("/app/product/findProductByCustomer")
        Observable<ProductDetailBean> findProductByCustomer(@FieldMap Map<Object, Object> map);

        //查询到具体商品的库存量等信息
        @FormUrlEncoded
        @POST("/app/repertory/findProductByAttributeValueIds")
        Observable<ProductStore> findProductByAttributeValueIds(@FieldMap Map<Object, Object> map);

        //删除浏览记录
        @FormUrlEncoded
        @POST("/app/product/deleteRecord")
        Observable<BaseReturnModel> deleteRecord(@FieldMap Map<Object, Object> map);

        //查询某款具体商品的价格,数量
        @FormUrlEncoded
        @POST("/app/product/findProduct")
        Observable<BaseReturnModel> findProduct(@FieldMap Map<Object, Object> map);

        //查看用户收藏的商品
        @FormUrlEncoded
        @POST("/app/product/findProductsByCustomer")
        Observable<ArrayList<Product>> findProductsByCustomer(@FieldMap Map<Object, Object> map);

        //查看店铺的商品
        @FormUrlEncoded
        @POST("/app/product/findProductsBySeller")
        Observable<ArrayList<Product>> findProductsBySeller(@FieldMap Map<Object, Object> map);

        //查看用户的浏览记录
        @FormUrlEncoded
        @POST("/app/product/findRecords")
        Observable<ArrayList<Product>> findRecords(@FieldMap Map<Object, Object> map);

        //查看用户收藏的店铺
        @FormUrlEncoded
        @POST("/app/seller/findSellersByCustomer")
        Observable<BaseReturnModel> findSellersByCustomer(@FieldMap Map<Object, Object> map);

        //附近实体店查询接口
        @FormUrlEncoded
        @POST("/app/customer/findStore")
        Observable<BaseReturnModel> findStore(@FieldMap Map<Object, Object> map);

        //批量删除购物车
        @FormUrlEncoded
        @POST("/app/cart/deleteCart")
        Observable<BaseReturnModel> deleteCart(@FieldMap Map<Object, Object> map);

        //查看用户的购物车
        @FormUrlEncoded
        @POST("/app/cart/findCartByCustomer")
        Observable<ArrayList<Product>> findCartByCustomer(@FieldMap Map<Object, Object> map);

        //购物车添加或者修改
        @FormUrlEncoded
        @POST("/app/cart/persist")
        Observable<BaseReturnModel> persist(@FieldMap Map<Object, Object> map);

        //Banner图展示
        @FormUrlEncoded
        @POST("/app/banner/findAll")
        Observable<BaseReturnModel> findAll(@FieldMap Map<Object, Object> map);

        //删除用户的某一条收货地址
        @FormUrlEncoded
        @POST("/app/address/delete")
        Observable<BaseReturnModel> delete(@FieldMap Map<Object, Object> map);

        //查看用户的收货地址
        @FormUrlEncoded
        @POST("/app/address/findByCustomer")
        Observable<ArrayList<AddressBean>> findByCustomer(@FieldMap Map<Object, Object> map);

        //添加/修改用户地址
        @FormUrlEncoded
        @POST("/app/address/persist")
        Observable<BaseReturnModel> persistAddress(@FieldMap Map<Object, Object> map);

        @Multipart
        @POST("/common/uploadPicture")
        Observable<BaseReturnModel> uploadFile(@Part("file\"; filename=\"real.jpg\"") RequestBody file);

    }

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            OkHttpClient httpClient = new OkHttpClient();
            httpClient.interceptors().add(new TokenInterceptor());
            sRetrofit = new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return sRetrofit;
    }


}



