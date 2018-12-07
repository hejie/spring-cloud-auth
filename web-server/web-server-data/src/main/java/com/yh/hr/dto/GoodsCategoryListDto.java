package com.yh.hr.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类
 */
@Data
public class GoodsCategoryListDto implements Serializable {

    //包含商品
    private List<GoodsCategoryDto> goodsCategoryList;
}
