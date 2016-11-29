package com.melvin.share.view;


import java.util.List;

/**
 * Created Time: 2016/7/24.
 * <p>
 * Author:Melvin
 * <p>
 * 功能：请求数据返回接口
 */
public interface RequestView<T> {
    void onRequestSuccess(List<T> data);

}
