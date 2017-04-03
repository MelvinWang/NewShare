package com.melvin.share.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期:2017/3/31 ${Time}
 * <p>
 * 作者:Melvin
 * <p>
 * 功能描述:
 */
public class CategoryBean extends BaseModel  {

    /**
     * categoryId : 0
     * categoryName : string
     * categoryPicture : string
     * mainProducts : [{"id":0,"mainPicture":"string","name":"string","place":"string","price":0,"shareTimes":0}]
     */

    public String categoryId;
    public String categoryName;
    public String categoryPicture;
    public List<MainProductsBean> mainProducts=new ArrayList<>();
    public static class MainProductsBean {
        /**
         * id : 0
         * mainPicture : string
         * name : string
         * place : string
         * price : 0
         * shareTimes : 0
         */
        public String id;
        public String mainPicture;
        public String name;
        public String place;
        public String price;
        public String shareTimes;
    }
}
