package com.melvin.share.network;


import com.melvin.share.Utils.Utils;
import com.melvin.share.app.BaseApplication;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

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
