package com.melvin.share.model.serverReturn;

import com.melvin.share.model.BaseModel;

import java.util.List;

/**
 * Created Time: 2016/8/26.
 * <p/>
 * Author:Melvin
 * <p/>
 * 功能：商品详情
 */
public class ProductDetailBean {


    /**
     * picture : /repository/niuzaiku1.png|/repository/niuzaiku2.png|/repository/niuzaiku3.png
     * id : 1
     * level : 10
     * productTotal : 1000
     * shareTimes : 321
     * productName : 牛仔裤
     * postage : 10
     */

    public ProductBean product;
    /**
     * id : 1
     * detailName : 主要材质
     * detailValue : 棉
     */

    public List<DetailsBean> details;
    /**
     * id : 1
     * attributeName : 颜色
     * attributeValues : [{"id":3,"attributeValueName":"蓝色"},{"id":4,"attributeValueName":"黑色"}]
     */

    public List<AttributesBean> attributes;

    public static class ProductBean {
        public String picture;
        public int id;
        public int level;
        public int price;
        public int productTotal;
        public int shareTimes;
        public String productName;
        public int postage;

        @Override
        public String toString() {
            return "ProductBean{" +
                    "picture='" + picture + '\'' +
                    ", id=" + id +
                    ", level=" + level +
                    ", productTotal=" + productTotal +
                    ", shareTimes=" + shareTimes +
                    ", productName='" + productName + '\'' +
                    ", postage=" + postage +
                    '}';
        }
    }

    public static class DetailsBean extends BaseModel {
        public int id;
        public String detailName;
        public String detailValue;

        @Override
        public String toString() {
            return "DetailsBean{" +
                    "id=" + id +
                    ", detailName='" + detailName + '\'' +
                    ", detailValue='" + detailValue + '\'' +
                    '}';
        }
    }

    public static class AttributesBean extends BaseModel{
        public int id;
        public String attributeName;

        @Override
        public String toString() {
            return "AttributesBean{" +
                    "id=" + id +
                    ", attributeName='" + attributeName + '\'' +
                    ", attributeValues=" + attributeValues +
                    '}';
        }

        /**
         * id : 3
         * attributeValueName : 蓝色
         */
        public List<AttributeValuesBean> attributeValues;

        public static class AttributeValuesBean extends BaseModel {
            public int id;
            public String attributeValueName;

            @Override
            public String toString() {
                return "AttributeValuesBean{" +
                        "id=" + id +
                        ", attributeValueName='" + attributeValueName + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "ProductDetailBean{" +
                "product=" + product +
                ", details=" + details +
                ", attributes=" + attributes +
                '}';
    }
}
