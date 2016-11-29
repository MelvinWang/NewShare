package com.melvin.share.network;


import com.melvin.share.Utils.Utils;
import com.melvin.share.app.BaseApplication;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created Time: 2016/7/17
 * <p>
 * Author:Melvin
 * <p>
 * 功能：Token拦截器
 */
public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (loadAuthorization() != null) {
            request = request.newBuilder().addHeader("token", loadAuthorization()).build();
        }
        Response response = chain.proceed(request);
        return response;
    }

    private String loadAuthorization() {
        String token = Utils.getToken(BaseApplication.getApplication());
        return token;

    }

}
