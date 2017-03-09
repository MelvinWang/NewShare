package com.melvin.share.network;


import com.google.gson.JsonObject;
import com.melvin.share.model.Category;
import com.melvin.share.model.Product;
import com.melvin.share.model.customer.Customer;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.model.serverReturn.CommonReturnModel;
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
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
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
        /**
         * 个人信息
         */
        //会员注册验证接口
        @GET("/app/customer/findCutomerExitByPhoneOrUserName")
        Observable<CommonReturnModel> checkCustomer(@Query("phone") String phone,
                                                    @Query("userName") String userName);

        //短信验证码发送接口
        @FormUrlEncoded
        @POST("/sendMessageByPhone")
        Observable<CommonReturnModel> sendMessage(@FieldMap Map<Object, Object> map);

        //会员注册接口
        @POST("/app/customer/insert")
        Observable<CommonReturnModel> regist(@Body JsonObject json);

        //修改密码
        @POST("/app/customer/updatePassword")
        Observable<CommonReturnModel> updatePassword(@Body JsonObject json);

        //会员账号登录
        @GET("/app/customer/loginByPassword")
        Observable<CommonReturnModel<SelfInformation>> loginByPassword(@Query("account") String account,
                                                                       @Query("password") String password);

        //会员凭手机验证码登录接口
        @GET("/app/customer/loginByPhoneCode")
        Observable<CommonReturnModel<SelfInformation>> loginByPhoneCode(@Query("phone") String phone,

                                                                        @Query("code") String password);

        //忘记密码
        @FormUrlEncoded
        @POST("/app/customer/forgetPassword")
        Observable<CommonReturnModel> forgetPassword(@FieldMap Map<Object, Object> map);

        //获取个人基本信息
        @GET("/app/customer/findCustomerById")
        Observable<Customer> findCustomerById(@Query("id") String id);

        //修改个人基本信息
        @POST("/app/customer/updateCutomerById")
        Observable<CommonReturnModel> updateCutomerById(@Body JsonObject json);


        /**
         * 收货地址
         */
        //查看用户的收货地址
        @GET("/app/address/findAddressByCustomerId")
        Observable<ArrayList<AddressBean>> findAddressByCustomerId(@Query("customerId") String customerId);

        //添加用户地址
        @POST("/app/address/insertAddressByCustomerId")
        Observable<CommonReturnModel> insertAddressByCustomerId(@Body JsonObject json);

        //设置默认收货地址
        @FormUrlEncoded
        @POST("/app/address/updateDefaultAddress")
        Observable<CommonReturnModel> updateDefaultAddress(@FieldMap Map<Object, Object> map);

        //修改用户地址
        @POST("/app/address/updateAddressByAddressId")
        Observable<CommonReturnModel> updateAddressByAddressId(@Body JsonObject json);

        //删除用户地址
        @DELETE("/app/address/deleteAddressByIds")
        Observable<CommonReturnModel> deleteAddressByIds(@Query("addressIds") String[] addressIds);


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
        Observable<CommonReturnModel> deleteRecord(@FieldMap Map<Object, Object> map);

        //查询某款具体商品的价格,数量
        @FormUrlEncoded
        @POST("/app/product/findProduct")
        Observable<CommonReturnModel> findProduct(@FieldMap Map<Object, Object> map);

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
        Observable<CommonReturnModel> findSellersByCustomer(@FieldMap Map<Object, Object> map);

        //附近实体店查询接口
        @FormUrlEncoded
        @POST("/app/customer/findStore")
        Observable<CommonReturnModel> findStore(@FieldMap Map<Object, Object> map);

        //批量删除购物车
        @FormUrlEncoded
        @POST("/app/cart/deleteCart")
        Observable<CommonReturnModel> deleteCart(@FieldMap Map<Object, Object> map);

        //查看用户的购物车
        @FormUrlEncoded
        @POST("/app/cart/findCartByCustomer")
        Observable<ArrayList<Product>> findCartByCustomer(@FieldMap Map<Object, Object> map);

        //购物车添加或者修改
        @FormUrlEncoded
        @POST("/app/cart/persist")
        Observable<CommonReturnModel> persist(@FieldMap Map<Object, Object> map);

        //Banner图展示
        @FormUrlEncoded
        @POST("/app/banner/findAll")
        Observable<CommonReturnModel> findAll(@FieldMap Map<Object, Object> map);


        @Multipart
        @POST("/common/uploadPicture")
        Observable<CommonReturnModel> uploadFile(@Part("file\"; filename=\"real.jpg\"") RequestBody file);

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



