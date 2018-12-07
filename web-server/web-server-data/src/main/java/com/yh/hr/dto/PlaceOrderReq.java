package com.yh.hr.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PlaceOrderReq implements Serializable {


    private List<OrderGoodsReq> goods;

    @Data
    public static class OrderGoodsReq {
        //商品
        private String goodsId;
        //数量
        private int num = 1;
    }
}
