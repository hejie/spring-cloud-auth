package com.yh.hr.controller;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.yh.hr.domain.QGoodsCategory;
import com.yh.hr.dto.CategoryReq;
import com.yh.hr.dto.GoodsCategoryListDto;
import com.yh.hr.dto.HttpResponse;
import com.yh.hr.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("category")
public class GoodsCategoryController {

    @Autowired
    private GoodsService goodsService;


    @PostMapping({"/list", "/count"})
    public HttpResponse<GoodsCategoryListDto> getGoodsCategory(@RequestBody CategoryReq req) {

        Predicate pre = QGoodsCategory.goodsCategory.isNotNull();

        if (!StringUtils.isEmpty(req.getId())) {
            pre = ExpressionUtils.and(pre, QGoodsCategory.goodsCategory.id.eq(req.getId()));
        }
        return goodsService.findByPredicate(pre);
    }

}
