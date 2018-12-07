package com.yh.hr.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
public class PointsRecordDto implements Serializable {
    //订单
    @JsonInclude(Include.NON_EMPTY)
    private String orderId;
    //积分
    private int points;
    //操作类型 加、减积分
    private int actionType;
    //ID
    @JsonInclude(Include.NON_EMPTY)
    private String goodsId;
    //商品名称
    @JsonInclude(Include.NON_EMPTY)
    private String goodsName;
    //主图
    @JsonInclude(Include.NON_EMPTY)
    private String mainImageId;
    //时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
    /**
     * 备注
     */
    private String remarks;

}
