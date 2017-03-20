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
public class ProductDetailBean extends BaseModel {


    /**
     * id : 1
     * name : 小米手机
     * mainPicture : /repository/xiaomi.png
     * otherPicture :
     * place : 北京
     * price : 688
     * shareTimes : 300
     * total : 1000
     * properties : [{"propertyName":"主屏分辨率","propertyValue":"1920x1080像素"},{"propertyName":"屏幕","propertyValue":"5.5英寸"},{"propertyName":"后置摄像头","propertyValue":"1200万像素"},{"propertyName":"电池容量","propertyValue":" 2860mAh"}]
     * attributes : [{"attributeName":"内存","attributeValues":[{"attributeValueId":1,"attributeValue":"1G"},{"attributeValueId":2,"attributeValue":"2G"}]},{"attributeName":"颜色","attributeValues":[{"attributeValueId":3,"attributeValue":"红色"},{"attributeValueId":4,"attributeValue":"白色"},{"attributeValueId":5,"attributeValue":"黑色"}]}]
     */

    public String id;
    public String name;
    public boolean collected;
    public String mainPicture;
    public String otherPicture;
    public String place;
    public String price;
    public String shareTimes;
    public String total;
    public String userId;
    public List<PropertiesBean> properties;
    public List<AttributesBean> attributes;


    public static class PropertiesBean extends BaseModel {
        public String propertyName;
        public String propertyValue;


    }

    public static class AttributesBean extends BaseModel {
        public String attributeName;
        public List<AttributeValuesBean> attributeValues;

        public static class AttributeValuesBean extends BaseModel {
            public String attributeValueId;
            public String attributeValue;
        }
    }
}
