package com.melvin.share.network;


import com.google.gson.JsonObject;
import com.melvin.share.model.Category;
import com.melvin.share.model.CategoryBean;
import com.melvin.share.model.MessageInfo;
import com.melvin.share.model.Product;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.customer.Customer;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.list.HomeHotProduct;
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
        @POST("/app/customer/loginByPassword")
        Observable<CommonReturnModel<SelfInformation>> loginByPassword(@Body JsonObject json);

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

        //查看用户的收货地址
        @GET("/app/address/findDefaultAddressByCustomerId")
        Observable<AddressBean> findDefaultAddressByCustomerId(@Query("customerId") String customerId);

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


        /**
         * 店铺相关东西
         */

        //查询收藏商店
        @POST("/app/user/findCollectUserByCustomerId")
        Observable<CommonList<ShopBean>> findCollectUserByCustomerId(@Body JsonObject json);

        //通过店铺ID查询店铺信息接口
        @GET("/app/user/findUserById")
        Observable<ShopBean> findShopById(@Query("userId") String userId,
                                          @Query("customerId") String customerId);

        //按商家查询商品接口
        @POST("/app/product/findProductByUser")
        Observable<CommonList<Product>> findProductByUser(@Body JsonObject json);

        //收藏或者取消收藏接口
        @FormUrlEncoded
        @POST("/app/user/collectUserOrDeleteUser")
        Observable<CommonReturnModel> collectUserOrDeleteUser(@FieldMap Map<Object, Object> map);

        //批量删除收藏店铺
        @DELETE("/app/user/deleteCollectUserByIds")
        Observable<CommonReturnModel> deleteCollectUserByIds(@Query("collectionUserIds") String[] collectionUserIds);

        //推荐商家
        @GET("/app/user/findRecommendedUser")
        Observable<ArrayList<ShopBean>> findRecommendedSeller();

        //推荐分类
        @POST("/app/product/findMainPageProduct")
        Observable<CommonList<CategoryBean>> findMainPageProduct(@Body JsonObject json);

        /**
         * 商品相关东西
         */

        //首页推荐商家
        @POST("/app/product/findHotProduct")
        Observable<CommonList<HomeHotProduct>> findHotProduct(@Body JsonObject json);

        //查看商品详情

        @GET("/app/product/findProductDetail")
        Observable<ProductDetailBean> findProductDetail(@Query("id") String id,
                                                        @Query("customerId") String customerId);

        //收藏商品或者取消收藏商品接口
        @FormUrlEncoded
        @POST("/app/product/collectProductOrDeleteProduct")
        Observable<CommonReturnModel> collectProductOrDeleteProduct(@FieldMap Map<Object, Object> map);


        //查询收藏商品接口
        @POST("/app/product/findCollectProduct")
        Observable<CommonList<Product>> findCollectProduct(@Body JsonObject json);

        //查询到具体商品的库存量等信息
        @FormUrlEncoded
        @POST("/app/stock/findStockByAttributeValueIds")
        Observable<ProductStore> findProductByAttributeValueIds(@FieldMap Map<Object, Object> map);


        /**
         * 消息操作接口
         */
        //用户消息中心
        @POST("/app/news/findNewsByCustomerId")
        Observable<CommonList<MessageInfo>> findNewsByCustomerId(@Body JsonObject json);


        /**
         * 购物车
         */
        //查看用户的购物车
        @POST("/app/cart/findCartByCustomer")
        Observable<CommonList<Product>> findCartByCustomer(@Body JsonObject json);

        //购物车添加
        @POST("/app/cart/insertProductToCart")
        Observable<CommonReturnModel> insertProductToCart(@Body JsonObject json);

        //购物车修改
        @POST("/app/cart/updateCartProduct")
        Observable<CommonReturnModel> updateCartProduct(@Body JsonObject json);

        //批量删除购物车
        @DELETE("/app/cart/deleteCartByIds")
        Observable<CommonReturnModel> deleteCartByIds(@Query("cartIds") String[] collectionUserIds);


        /**
         * 订单
         */
        //确认订单
        @POST("/app/order/makeSureOrder")
        Observable<CommonReturnModel<WaitPayOrderInfo>> makeSureOrder(@Body JsonObject json);
        //查看全部订单
        @POST("/app/order/findOrderByCustomer")
        Observable<CommonList<WaitPayOrderInfo.OrderBean>> findOrderByCustomer(@Body JsonObject json);














        //首页推荐
        @FormUrlEncoded
        @POST("/app/product/findProductsInHomePage")
        Observable<ArrayList<Product>> findProductsInHomePage(@FieldMap Map<Object, Object> map);


        //查看分类下的商品
        @FormUrlEncoded
        @POST("/app/product/findProductsByCategory")
        Observable<ArrayList<Product>> findProductsByCategory(@FieldMap Map<Object, Object> map);


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



