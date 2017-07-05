package com.melvin.share.network;


import com.google.gson.JsonObject;
import com.melvin.share.model.CategoryBean;
import com.melvin.share.model.Evaluation;
import com.melvin.share.model.LogisticsModel;
import com.melvin.share.model.MessageInfo;
import com.melvin.share.model.PicturePath;
import com.melvin.share.model.Product;
import com.melvin.share.model.RefundModel;
import com.melvin.share.model.Reward;
import com.melvin.share.model.ScanProduct;
import com.melvin.share.model.WaitPayOrderInfo;
import com.melvin.share.model.WalletProduct;
import com.melvin.share.model.customer.Customer;
import com.melvin.share.model.list.CommonList;
import com.melvin.share.model.list.HomeHotProduct;
import com.melvin.share.model.serverReturn.AddressBean;
import com.melvin.share.model.serverReturn.CommonReturnModel;
import com.melvin.share.model.serverReturn.DepositRecordBean;
import com.melvin.share.model.serverReturn.OnlineStore;
import com.melvin.share.model.serverReturn.ProductDetailBean;
import com.melvin.share.model.serverReturn.ProductStore;
import com.melvin.share.model.serverReturn.SelfInformation;
import com.melvin.share.model.serverReturn.ShopBean;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
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
        @POST("/app/customer/findCutomerExitByPhoneOrUserName")
        Observable<CommonReturnModel> checkCustomer(@Body JsonObject json);

        //短信验证码发送接口
        @FormUrlEncoded
        @POST("/sendMessageByPhone")
        Observable<CommonReturnModel> sendMessage(@FieldMap Map<String, Object> map);

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
        Observable<CommonReturnModel> forgetPassword(@FieldMap Map<String, Object> map);

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

        //查看用户的默认收货地址
        @GET("/app/address/findDefaultAddressByCustomerId")
        Observable<AddressBean> findDefaultAddressByCustomerId(@Query("customerId") String customerId);

        //添加用户地址
        @POST("/app/address/insertAddressByCustomerId")
        Observable<CommonReturnModel> insertAddressByCustomerId(@Body JsonObject json);

        //设置默认收货地址
        @FormUrlEncoded
        @POST("/app/address/updateDefaultAddress")
        Observable<CommonReturnModel> updateDefaultAddress(@FieldMap Map<String, Object> map);

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

        //扫描店铺二维码
        @GET("/app/scanCode/scanUser")
        Observable<CommonReturnModel<ShopBean>> scanUser(@Query("customerId") String customerId,
                                                         @Query("code") String code);

        //附近实体店查询接口,参数:longitude为经度,latitude为纬度
        @GET("/app/customer/findStore")
        Observable<ArrayList<OnlineStore>> findStore(@Query("latitude") String latitude,
                                                     @Query("longitude") String longitude);


        //按商家查询商品接口
        @POST("/app/product/findProductByUser")
        Observable<CommonList<Product>> findProductByUser(@Body JsonObject json);

        //收藏或者取消收藏接口
        @FormUrlEncoded
        @POST("/app/user/collectUserOrDeleteUser")
        Observable<CommonReturnModel> collectUserOrDeleteUser(@FieldMap Map<String, Object> map);

        //批量删除收藏商品
        @DELETE("/app/product/deleteCollectByIds")
        Observable<CommonReturnModel> deleteCollectByIds(@Query("collectIds") String[] collectIds);

        //批量删除收藏店铺
        @DELETE("/app/user/deleteCollectUserByIds")
        Observable<CommonReturnModel> deleteCollectUserByIds(@Query("collectionUserIds") String[] collectionUserIds);

        //推荐商家
        @GET("/app/user/findRecommendedUser")
        Observable<ArrayList<ShopBean>> findRecommendedSeller();

        //推荐分类
        @POST("/app/product/findMainPageProduct")
        Observable<CommonList<CategoryBean>> findMainPageProduct(@Body JsonObject json);

        //按分类查询商品接口
        @POST("/app/product/findProductByCategory")
        Observable<CommonList<Product>> findProductByCategory(@Body JsonObject json);

        //关键字全文检索商品
        @POST("/app/product/searchProductByKeywords")
        Observable<CommonList<Product>> searchProductByKeywords(@Body JsonObject json);

        /**
         * 商品相关东西
         */

        //首页推荐商品 按分享热度查询商品接口
        @POST("/app/product/findHotProduct")
        Observable<CommonList<HomeHotProduct>> findHotProduct(@Body JsonObject json);

        //历史浏览记录接口
        @POST("/app/product/findHistoryProduct")
        Observable<CommonList<HomeHotProduct>> findHistoryProduct(@Body JsonObject json);

        //查看商品详情
        @GET("/app/product/findProductDetail")
        Observable<ProductDetailBean> findProductDetail(@Query("id") String id,
                                                        @Query("customerId") String customerId);

        //查看商品详情,通过扫码
        @GET("/app/scanCode/scanOrderItem")
        Observable<CommonReturnModel<ScanProduct>> scanOrderItem(@Query("code") String code,
                                                                 @Query("customerId") String customerId);

        //收藏商品或者取消收藏商品接口
        @FormUrlEncoded
        @POST("/app/product/collectProductOrDeleteProduct")
        Observable<CommonReturnModel> collectProductOrDeleteProduct(@FieldMap Map<String, Object> map);


        //查询收藏商品接口
        @POST("/app/product/findCollectProduct")
        Observable<CommonList<Product>> findCollectProduct(@Body JsonObject json);

        //查询到具体商品的库存量等信息
        @FormUrlEncoded
        @POST("/app/stock/findStockByAttributeValueIds")
        Observable<ProductStore> findProductByAttributeValueIds(@FieldMap Map<String, Object> map);

        /**
         * 商品评价
         */

        //通过商品查询出商品评价
        @POST("/app/evaluation/findEvaluationsByProduct")
        Observable<CommonList<Evaluation>> findEvaluationsByProduct(@Body JsonObject json);

        //评价订单明细商品
        @POST("/app/evaluation/insertOderItemEvaluation")
        Observable<CommonReturnModel> insertOderItemEvaluation(@Body JsonObject json);

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
        //确认订单 购物车
        @POST("/app/order/makeSureOrder")
        Observable<CommonReturnModel<WaitPayOrderInfo>> makeSureOrder(@Body JsonObject json);

        //确认订单  直接购买
        @POST("/app/order/directBuyProduct")
        Observable<CommonReturnModel<WaitPayOrderInfo>> directBuyProduct(@Body JsonObject json);

        //查看全部订单
        @POST("/app/order/findOrderByCustomer")
        Observable<CommonList<WaitPayOrderInfo.OrderBean>> findOrderByCustomer(@Body JsonObject json);

        //查看单个订单的详情
        @GET("/app/order/findOrderById")
        Observable<WaitPayOrderInfo.OrderBean> findOrderById(@Query("id") String id);
        //订单删除
        @POST("/app/order/deleteOrder")
        Observable<CommonReturnModel> deleteOrder(@Query("orderId") String id);

        //改变订单状态已经付款
        @POST("/app/order/updateOrderStatus")
        Observable<WaitPayOrderInfo.OrderBean> updateOrderStatus(@Body JsonObject json);

        //订单确认收货
        @POST("/app/order/makeSureOrderReceived")
        Observable<CommonReturnModel> makeSureOrderReceived(@Body JsonObject json);

        //订单明细确认收货
        @POST("/app/order/makeSureOrderItemReceived")
        Observable<CommonReturnModel> makeSureOrderItemReceived(@Body JsonObject json);

        //通过订单详情物流查询接口
        @GET("/common/logist/findLogistByOrderItemId")
        Observable<CommonReturnModel<LogisticsModel>> findLogistByOrderItemId(@Query("orderItemId") String id);

        //申请售后
        @POST("/app/sellService/applyOrderItemSellService")
        Observable<CommonReturnModel> applyOrderItemSellService(@Body JsonObject json);
        //订单详情售后查看接口
        @GET("/app/sellService/findSellServiceByOrderItemId")
        Observable<RefundModel> findSellServiceByOrderItemId(@Query("orderItemId") String orderItemId);

        /**
         * 返利
         */
        //查看单个订单的详情
        @GET("/app/reward/findMyTotalReward")
        Observable<Reward> findMyTotalReward(@Query("customerId") String id);

        //POST  查看个人返利的商品
        @POST("/app/reward/findMyReward")
        Observable<CommonList<WalletProduct>> findMyReward(@Body JsonObject json);

        //POST  商品二维码列表
        @POST("/app/scanCode/findProductScanCode")
        Observable<CommonList<Product>> findProductScanCode(@Body JsonObject json);

        //POST  店铺二维码列表
        @POST("/app/scanCode/findUserScanCode")
        Observable<CommonList<ShopBean>> findUserScanCode(@Body JsonObject json);


        //  申请成为体验馆
        @POST("/app/customer/applyExperience")
        Observable<CommonReturnModel> applyExperience(@Body JsonObject json);

        //  申请成为店铺接口
        @POST("/app/user/applyUser")
        Observable<CommonReturnModel> applyUser(@Body JsonObject json);


        //首页推荐
        @FormUrlEncoded
        @POST("/app/product/findProductsInHomePage")
        Observable<ArrayList<Product>> findProductsInHomePage(@FieldMap Map<String, Object> map);


        //查看分类下的商品
        @FormUrlEncoded
        @POST("/app/product/findProductsByCategory")
        Observable<ArrayList<Product>> findProductsByCategory(@FieldMap Map<String, Object> map);


        //删除浏览记录
        @FormUrlEncoded
        @POST("/app/product/deleteRecord")
        Observable<CommonReturnModel> deleteRecord(@FieldMap Map<String, Object> map);

        //查询某款具体商品的价格,数量
        @FormUrlEncoded
        @POST("/app/product/findProduct")
        Observable<CommonReturnModel> findProduct(@FieldMap Map<String, Object> map);

        //查看用户收藏的商品
        @FormUrlEncoded
        @POST("/app/product/findProductsByCustomer")
        Observable<ArrayList<Product>> findProductsByCustomer(@FieldMap Map<String, Object> map);

        //查看店铺的商品
        @FormUrlEncoded
        @POST("/app/product/findProductsBySeller")
        Observable<ArrayList<Product>> findProductsBySeller(@FieldMap Map<String, Object> map);

        //查看用户的浏览记录
        @FormUrlEncoded
        @POST("/app/product/findRecords")
        Observable<ArrayList<Product>> findRecords(@FieldMap Map<String, Object> map);

        //查看用户收藏的店铺
        @FormUrlEncoded
        @POST("/app/seller/findSellersByCustomer")
        Observable<CommonReturnModel> findSellersByCustomer(@FieldMap Map<String, Object> map);

        //附近实体店查询接口
        @FormUrlEncoded
        @POST("/app/customer/findStore")
        Observable<CommonReturnModel> findStore(@FieldMap Map<String, Object> map);


        //Banner图展示
        @FormUrlEncoded
        @POST("/app/banner/findAll")
        Observable<CommonReturnModel> findAll(@FieldMap Map<String, Object> map);


//        @Multipart
//        @POST("/common/picture/loadFileUpload")
//        Observable<CommonReturnModel<PicturePath>> uploadFile(@Part("picture\";filename=\"real.jpg\"") RequestBody file);
//        Observable<CommonReturnModel<PicturePath>> uploadFile(@Body MultipartBody imgs);
//        Observable<CommonReturnModel<PicturePath>> uploadFile(@Part("picture\";filename=\"real.jpg\"") RequestBody file);
//        Observable<CommonReturnModel<PicturePath>> uploadFile(@Body MultipartBody imgs);


        @Multipart
        @POST("/common/picture/loadFileUpload")
        Observable<CommonReturnModel<PicturePath>> uploadFile(@Part() MultipartBody.Part file);



        //查询提现记录
        @POST("/app/reward/findWithdrawCashByCustomer")
        Observable<CommonList<DepositRecordBean>> findWithdrawCashByCustomer(@Body JsonObject json);

        //个人提现
        @POST("/app/reward/withdrawCash")
        Observable<CommonReturnModel> withdrawCash(@Body JsonObject json);
    }

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
             OkHttpClient httpClient = new OkHttpClient();
            httpClient.newBuilder().addInterceptor(new TokenInterceptor());
//            httpClient.interceptors().add(new TokenInterceptor());
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



