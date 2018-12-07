package com.yh.hr.domain;


import com.google.common.base.MoreObjects;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 订单
 */
@Data
@Entity
@Table(name = "yh_hr_order")
public class Order extends IdEntity {
    //工号
    private String userCode;
    //用户id或者购买者
    private String openId;
    //成交价格
    private double dealPrice;
    //积分
    private int totalIntegral;
    //支付类型
    private int payType;
    //支付渠道
    private String payChannel;
    //第三方支付订单id
    private String thirdPartyPayOrderId;
    //第三方支付订单信息
    private String thirdPartyPayInfo;
    //收货地址
    private String shippingAddress;
    //订单状态
    //兑换状态【100:待兑换;101:待核销;102:已核销;103:兑换失败;104:核销失败】
    //在线支付【300:待付款;301:已付款;303:已完成;304:付款失败】
    private OrderStatus orderStatus;
    //是否预定单
    private boolean isPreOrder;
    //预定提货时间
    private String preOrderTime;
    //预留电话
    private String phone;
    //备注
    private String remarks;
    //订单商品集合
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderGoods> orderGoodsList;


    public enum OrderStatus {
        //===核销状态===
        WAIT_EXCHANGE(101, "待兑换"),
        WAIT_WRITE_OFF(102, "待核销"),
        ALREADY_WRITE_OFF(103, "已核销"),
        EXCHANGE_FAILED(104, "兑换失败"),
        WRITE_OFF_FAILED(105, "核销失败"),

        //付款状态
        WAIT_PAYMENT(300, "待付款"),
        ALREADY_PAYMENT(301, "已付款"),
        COMPLETED(302, "已完成"),
        PAYMENT_FAILED(303, "付款失败");


        public int code;
        public String name;

        OrderStatus(int code, String name) {
            this.code = code;
            this.name = name;
        }


    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }

}
