package com.yh.hr.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderListDto implements Serializable {


    private List<OrderDto> orderList;

    private CPage page;
}
