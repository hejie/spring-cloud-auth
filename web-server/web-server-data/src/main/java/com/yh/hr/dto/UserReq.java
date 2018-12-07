package com.yh.hr.dto;

import lombok.Data;

@Data
public class UserReq {

    //openId
    private String openId;
    //工号
    private String userCode;
    //身份证后六位
    private String idCard;

}
