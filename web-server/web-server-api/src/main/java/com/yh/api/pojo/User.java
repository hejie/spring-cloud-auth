package com.yh.api.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    //工号/ID
    private String userCode;
    //姓名
    private String userName;
    //密码
    private String password;
    //手机
    private String mobile;
    //性别
    private String sex;
    //生日
    private Date birthday;
    //openId
    private String wxOpenid;
    //昵称
    private String nickName;
    //头像
    private String avatar;
    //状态
    private String status;


}
