package com.yh.hr.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsReq implements Serializable {


    int page;

    int size;

    String goodsId;

}
