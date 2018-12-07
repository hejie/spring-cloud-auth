package com.yh.hr.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类
 */
@Data
public class GoodsCategoryDto implements Serializable {

    //ID
    private String id;
    //父类目
    private String parentId;
    //类目名称
    private String name;
    //类目图片
    private String image;
    //是否包含子类目
    private boolean hasSubCategory;
    //包含商品
    private List<GoodsDto> goodsList;
}
