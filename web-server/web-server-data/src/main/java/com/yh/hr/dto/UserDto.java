package com.yh.hr.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {


    //工号
    private String userCode;
    //姓名
    private String userName;
    //机构/部门
    private String officeName;
    //生日
    private Date birthday;
    //昵称
    private String nickName;
    //头像
    private String avatar;
    //可用积分
    private long availablePoints;

}
