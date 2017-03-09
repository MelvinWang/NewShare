package com.melvin.share.model.customer;

/**
 * 创建日期:2017/3/9 ${Time}
 * <p>
 * 作者:Melvin
 * <p>
 * 功能描述:
 */
public class Customer{
    public String id;
    public String customerId;
    public String userName;
    public String realName;
    public String nickName;
    public String picture;
    public String phone;
    public String sex;
    public String birthday;
    public String domicile;

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", picture='" + picture + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", domicile='" + domicile + '\'' +
                '}';
    }
}
