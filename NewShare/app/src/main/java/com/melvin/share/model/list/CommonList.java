package com.melvin.share.model.list;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期:2017/3/9 ${Time}
 * <p>
 * 作者:Melvin
 * <p>
 * 功能描述:
 */
public class CommonList<T> {

    /**
     * currentPage : 0
     * rows : [{"id":0,"name":"string","picture":"string"}]
     */

    public int currentPage;
    public List<T> rows=new ArrayList<>();
}
