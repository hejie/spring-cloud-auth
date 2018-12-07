package com.yh.hr.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPointsDto implements Serializable {


    //工号
    private String userCode;
    //姓名
    private String userName;
    //机构/部门
    private String officeName;
    //昵称
    private String nickName;
    //头像
    private String avatar;
    //累计积分总额
    private long totalPointsAmount;
    //可用积分
    private long availablePoints;

}
