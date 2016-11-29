package com.melvin.share.model.serverReturn;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/24
 * <p/>
 * 描述：
 */
public class BaseReturnModel<T>{
    public boolean success;
    public String message;
    public T result;
}
