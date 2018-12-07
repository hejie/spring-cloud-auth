package com.yh.hr.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "yh_hr_wx_user")
public class WxUser extends IdEntity {
    //openId
    private String openId;
    //昵称
    private String nickName;
    //头像
    private String avatar;
    //关注状态
    private boolean subscribe;


}
