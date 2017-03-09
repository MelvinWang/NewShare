package com.melvin.share.model.serverReturn;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/25
 * <p>
 * 描述：
 */
public class SelfInformation  {


    /**
     * customer : {"id":9,"userName":"melvin","nickName":null,"picture":null,"phone":"13883387332","sex":null,"birthday":null,"domicile":null}
     */

    public CustomerBean customer;
  
    public static class CustomerBean {
        public String id;
        public String userName;
        public String nickName;
        public String picture;
        public String phone;
        public String sex;
        public String birthday;
        public String domicile;
        
    }
}
