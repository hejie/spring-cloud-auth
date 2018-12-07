package com.yh.hr.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 商品类目
 */
@Data
@Entity
@Table(name = "yh_hr_goods_category")
public class GoodsCategory extends IdEntity {

    //类目父类ID
    @Column(columnDefinition = "BIGINT DEFAULT 0 ")
    private String parentId;
    //类目名称
    private String name;
    //类目图片
    private String image;
    //是否包含子类目
    private boolean hasSubCategory;

    //显示楼层
    private int floor;

    @OneToMany(mappedBy = "goodsCategory", fetch = FetchType.LAZY)
    private List<Goods> goodsList;

}
