package com.yh.hr.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "js_sys_user")
public class User {
    //工号/ID
    @Id
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
    @Column(length = 1)
    private String status;


}
