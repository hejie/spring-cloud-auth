package com.yh.hr.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "yh_hr_points_record")
public class PointsRecord extends IdEntity {

    /**
     * 订单Id
     */
    private String orderId;
    /**
     * 用户Id
     */
    @OneToOne
    @JoinColumn(name = "userCode")
    private User user;
    /**
     * 操作积分
     */
    private int points;
    /**
     * 有效期
     */
    private Date expiryDate;
    /**
     * 状态
     */
    private boolean pointsStatus;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 操作类型 加、减积分等等
     */
    private int actionType;

    /**
     * 状态
     */
    @Column(length = 1)
    private String status;
}
