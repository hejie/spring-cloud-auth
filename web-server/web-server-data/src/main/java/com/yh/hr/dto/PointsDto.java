package com.yh.hr.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PointsDto implements Serializable {

    /**
     * 用户Id
     */
    private String userCode;
    /**
     * 累计积分总额
     */
    private int totalPointsAmount;
    /**
     * 可用积分
     */
    private int availablePoints;
    /**
     * 有效期
     */
    private Date expiryDate;
    /**
     * 状态
     */
    private int pointsStatus;


}
