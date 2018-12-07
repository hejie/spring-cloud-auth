package com.yh.hr.dto;


import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品
 */
@Data
public class GoodsDto implements Serializable {

    //ID
    private String id;
    //编码
    private String code;
    //商品名称
    private String name;
    //商品描述
    private String description;
    //主图
    private String mainImageId;
    //商品多图[1,2]
    private List<String> multiImageId;
    //备注
    private String remarks;
    //价格
    @Column(columnDefinition = "Decimal(8,2) default '00.00'", scale = 2)
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = ".00")
    private BigDecimal price;
    //商品销量
    private int sales;
    //商品库存
    private int stock;
    //是否积分商品
    private boolean useIntegral;
    //商品积分
    private int integral;
}
