package com.yh.hr.controller;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.yh.hr.domain.QGoods;
import com.yh.hr.dto.GoodsDto;
import com.yh.hr.dto.GoodsListDto;
import com.yh.hr.dto.GoodsReq;
import com.yh.hr.dto.HttpResponse;
import com.yh.hr.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;


    @PostMapping({"/list", "/count"})
    public HttpResponse<GoodsListDto> getGoods(@RequestBody GoodsReq req) {

        Pageable page = PageRequest.of(req.getPage(), req.getSize(), Sort.Direction.DESC, "createDate");
        Predicate pre = QGoods.goods.isNotNull();

        return goodsService.findByPredicate(pre, page);
    }


    @PostMapping({"/detail"})
    public HttpResponse<GoodsDto> getGoodsDetail(@RequestBody GoodsReq req) {

        Predicate pre = QGoods.goods.isNotNull();
        String goodsId = req.getGoodsId();
        BooleanExpression expression = goodsId == null ? QGoods.goods.id.isNull() : QGoods.goods.id.eq(goodsId);
        pre = ExpressionUtils.and(pre, expression);
        return goodsService.findGoodsByPredicate(pre);
    }
}
