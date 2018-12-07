package com.yh.hr.domain;


import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 订单商品
 */
@Data
@Entity
@Table(name = "yh_hr_order_goods")
public class OrderGoods extends IdEntity {
    //订单id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderId")
    private Order order;
    //商品id
    private String goodsId;
    //商品名称
    private String goodsName;
    //主图
    private String mainImageId;
    //商品多图[1,2]
    private String multiImageId;
    //售价
    @Column(columnDefinition = "Decimal(8,2) default '00.00'", scale = 2)
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = ".00")
    private BigDecimal sellPrice;
    //成交价
    @Column(columnDefinition = "Decimal(8,2) default '00.00'", scale = 2)
    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = ".00")
    private BigDecimal dealPrice;
    //积分
    private int integral = 0;
    //数量
    private int num = 0;
    //折扣
    private float discount = 0f;
    //优惠券
    private long couponId;

}
