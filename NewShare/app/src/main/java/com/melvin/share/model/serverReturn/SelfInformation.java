package com.melvin.share.model.serverReturn;

import java.util.List;

/**
 * Author: Melvin
 * <p>
 * Data： 2016/8/25
 * <p>
 * 描述：
 */
public class SelfInformation  {


    public CustomerBean customer;

    public static class CustomerBean {
        public int id;
        public String username;
        public String nickname;
        public Object sex;
        public Object birthday;
        public String address;
        public String headPicture;
        public String phone;
        public int longitude;
        public int latitude;
        public boolean senior;
        public List<?> addresses;
        public List<?> rebates;
    }

}
