package com.yh.hr.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品列表
 */
@Data
public class GoodsListDto implements Serializable {

    //包含商品
    private List<GoodsDto> goodsList;
}
