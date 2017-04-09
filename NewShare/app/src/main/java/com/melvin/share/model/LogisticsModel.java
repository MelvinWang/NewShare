package com.melvin.share.model;

import java.util.ArrayList;

/**
 * 创建日期:2017/4/9 ${Time}
 * <p>
 * 作者:Melvin
 * <p>
 * 功能描述:
 */
public class LogisticsModel extends BaseModel {

    /**
     * logist : {"company":"韵达","com":"yd","no":"3925980113809","status":"1","list":[{"datetime":"2016-11-11 17:34:14","remark":"快件在 福建石狮市公司 进行揽件扫描","zone":""},{"datetime":"2016-11-11 17:52:13","remark":"快件在 福建石狮市公司 进行下级地点扫描，将发往：四川成都网点包","zone":""},{"datetime":"2016-11-11 19:45:54","remark":"快件在 福建石狮市公司 进行装车扫描，即将发往：四川成都分拨中心","zone":""},{"datetime":"2016-11-13 09:58:12","remark":"快件在 四川成都分拨中心 在分拨中心进行卸车扫描","zone":""},{"datetime":"2016-11-13 10:06:00","remark":"快件在 四川成都分拨中心 从站点发出，本次转运目的地：四川成都软件园公司","zone":""},{"datetime":"2016-11-13 14:31:41","remark":"快件在 四川成都软件园公司 进行派件扫描；派送业务员：红华；联系电话：15388178919","zone":""},{"datetime":"2016-11-13 19:12:18","remark":"快件在 四川成都软件园公司 快件已被 已签收 签收","zone":""}]}
     */

    public LogistBean logist;


    public static class LogistBean {
        /**
         * company : 韵达
         * com : yd
         * no : 3925980113809
         * status : 1
         * list : [{"datetime":"2016-11-11 17:34:14","remark":"快件在 福建石狮市公司 进行揽件扫描","zone":""},{"datetime":"2016-11-11 17:52:13","remark":"快件在 福建石狮市公司 进行下级地点扫描，将发往：四川成都网点包","zone":""},{"datetime":"2016-11-11 19:45:54","remark":"快件在 福建石狮市公司 进行装车扫描，即将发往：四川成都分拨中心","zone":""},{"datetime":"2016-11-13 09:58:12","remark":"快件在 四川成都分拨中心 在分拨中心进行卸车扫描","zone":""},{"datetime":"2016-11-13 10:06:00","remark":"快件在 四川成都分拨中心 从站点发出，本次转运目的地：四川成都软件园公司","zone":""},{"datetime":"2016-11-13 14:31:41","remark":"快件在 四川成都软件园公司 进行派件扫描；派送业务员：红华；联系电话：15388178919","zone":""},{"datetime":"2016-11-13 19:12:18","remark":"快件在 四川成都软件园公司 快件已被 已签收 签收","zone":""}]
         */

        public String company;
        public String com;
        public String no;
        public String status;
        public ArrayList<ListBean> list = new ArrayList<>();

        public static class ListBean extends BaseModel {
            /**
             * datetime : 2016-11-11 17:34:14
             * remark : 快件在 福建石狮市公司 进行揽件扫描
             * zone :
             */
            public String datetime;
            public String remark;
            public String zone;

        }
    }
}
