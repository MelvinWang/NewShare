package com.melvin.share.model.query;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/22
 * <p>
 * 描述：查看分类下的商品,参数:category.id必须传回,
 * 描述：剩下另外四个参数:默认综合传level,按星级排序,
 * 描述：传saleTotal为按销量排序,传price为按销量排序,false表示倒序,true表示顺序，
 * 描述：传shareTimes按销量排序,这四个参数只能传一个
 */
public class ProductQuery {

    public String id;
    public String level;//综合
    public String saleTotal;
    public boolean price = false;
    public String shareTimes;
}
