package com.yh.hr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "yh_hr_oauth_access_token")
public class Token extends IdEntity {

    /**
     * token
     */
    private String access_token;
    /**
     * 用户
     */
    private String userId;
    /**
     * 刷新
     */
    private String refresh_token;
    /**
     * 客户端
     */
    private String client_id;

    /**
     * 客户端
     */
    private String openId;

}
