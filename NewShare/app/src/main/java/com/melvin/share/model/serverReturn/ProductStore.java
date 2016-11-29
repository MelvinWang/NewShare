package com.melvin.share.model.serverReturn;

import com.melvin.share.model.BaseModel;

import java.util.List;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/31
 * <p/>
 * 描述：具体商品库存
 */
public class ProductStore extends  BaseModel {

    /**
     * id : 1
     * name : 蓝色L牛仔裤
     * price : 59
     * totalNum : 282
     * product : null
     * attributeValues : []
     */

    public int id;
    public String name;
    public int price;
    public int totalNum;
    public Object product;
    public List attributeValues;


}
