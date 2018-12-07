package com.yh.hr.dto;

import com.yh.hr.enums.Time;
import com.yh.hr.domain.Order;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderReq implements Serializable {

    //任务状态
    private Order.OrderStatus state;
    //任务时间
    private Time time;

    int page;

    int size;


}
