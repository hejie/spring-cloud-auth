package com.yh.hr.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "yh_hr_points")
public class Points extends IdEntity {

    /**
     * 用户Id
     */
    @OneToOne
    @JoinColumn(name = "userCode")
    private User user;
    /**
     * 累计积分总额
     */
    private int totalPointsAmount;
    /**
     * 积分总额
     */
    private int availablePoints;
    /**
     * 有效期
     */
    private Date expiryDate;
    /**
     * 状态
     */
    private String pointsStatus;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 状态
     */
    private String status;
}
