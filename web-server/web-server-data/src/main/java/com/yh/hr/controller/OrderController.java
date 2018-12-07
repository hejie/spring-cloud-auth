package com.yh.hr.controller;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.yh.hr.enums.Time;
import com.yh.hr.domain.QOrder;
import com.yh.hr.dto.HttpResponse;
import com.yh.hr.dto.OrderListDto;
import com.yh.hr.dto.OrderReq;
import com.yh.hr.dto.PlaceOrderReq;
import com.yh.hr.service.OrderService;
import com.yh.hr.utils.TimeUtils;
import com.yh.hr.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping({"/list", "/count"})
    public HttpResponse<OrderListDto> getOrderList(@RequestBody OrderReq req, @RequestParam("access_token") String access_token) {

        Pageable page = PageRequest.of(req.getPage(), req.getSize(), Sort.Direction.DESC, "createDate");
        Predicate pre = QOrder.order.isNotNull();

        StringTemplate dateExpr = Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", QOrder.order.createDate);
        if (req.getTime() == Time.TODAY) {
            pre = ExpressionUtils.and(pre, dateExpr.eq(TimeUtils.getDate()));
        } else if (req.getTime() == Time.OTHER) {
            pre = ExpressionUtils.and(pre, dateExpr.lt(TimeUtils.getDate()));
        }
        String userCode = TokenUtil.getUserCodeFromToken(access_token);
        if (!StringUtils.isEmpty(userCode)) {
            pre = ExpressionUtils.and(pre, QOrder.order.userCode.eq(userCode));
        }
        return orderService.findByPredicate(pre, page);
    }


    @PostMapping({"/placeOrder"})
    public HttpResponse placeOrder(@RequestBody PlaceOrderReq req, @RequestParam("access_token") String access_token) {
        String userCode = TokenUtil.getUserCodeFromToken(access_token);
        return orderService.placeOrder(req, userCode);

    }

}
