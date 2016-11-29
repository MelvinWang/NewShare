package com.melvin.share.model.serverReturn;

import java.util.List;

/**
 * Author: Melvin
 * <p/>
 * Data： 2016/8/25
 * <p/>
 * 描述：
 */
public class LoginReturn extends BaseReturnModel {


    private ResultBean result;
    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 4
         * username : melcin
         melvin
         * nickname :
         * sex : null
         * birthday : null
         * address :
         * headPicture :
         * phone : 13883387332
         * longitude : 0
         * latitude : 0
         * senior : false
         * addresses : []
         * rebates : []
         */

        private CustomerBean customer;

        public CustomerBean getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerBean customer) {
            this.customer = customer;
        }

        public static class CustomerBean {
            private int id;
            private String username;
            private String nickname;
            private Object sex;
            private Object birthday;
            private String address;
            private String headPicture;
            private String phone;
            private int longitude;
            private int latitude;
            private boolean senior;
            private List<?> addresses;
            private List<?> rebates;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public Object getBirthday() {
                return birthday;
            }

            public void setBirthday(Object birthday) {
                this.birthday = birthday;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getHeadPicture() {
                return headPicture;
            }

            public void setHeadPicture(String headPicture) {
                this.headPicture = headPicture;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getLongitude() {
                return longitude;
            }

            public void setLongitude(int longitude) {
                this.longitude = longitude;
            }

            public int getLatitude() {
                return latitude;
            }

            public void setLatitude(int latitude) {
                this.latitude = latitude;
            }

            public boolean isSenior() {
                return senior;
            }

            public void setSenior(boolean senior) {
                this.senior = senior;
            }

            public List<?> getAddresses() {
                return addresses;
            }

            public void setAddresses(List<?> addresses) {
                this.addresses = addresses;
            }

            public List<?> getRebates() {
                return rebates;
            }

            public void setRebates(List<?> rebates) {
                this.rebates = rebates;
            }
        }
    }
}
