package com.yh.hr.controller;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.yh.hr.enums.Time;
import com.yh.hr.domain.QPoints;
import com.yh.hr.domain.QPointsRecord;
import com.yh.hr.dto.HttpResponse;
import com.yh.hr.dto.PointsListDto;
import com.yh.hr.dto.PointsRecordListDto;
import com.yh.hr.dto.PointsReq;
import com.yh.hr.service.PointsService;
import com.yh.hr.utils.TimeUtils;
import com.yh.hr.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("points")
public class PointsController {

    @Autowired
    private PointsService pointsService;


    @PostMapping({"/list", "/count"})
    public HttpResponse<PointsRecordListDto> getPointsRecords(@RequestBody PointsReq req, @RequestParam("access_token") String access_token) {

        Pageable page = PageRequest.of(req.getPage(), req.getSize(), Sort.Direction.DESC, "createDate");
        Predicate pre = QPointsRecord.pointsRecord.isNotNull();

        StringTemplate dateExpr = Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", QPoints.points.createDate);
        if (req.getTime() == Time.TODAY) {
            pre = ExpressionUtils.and(pre, dateExpr.eq(TimeUtils.getDate()));
        } else if (req.getTime() == Time.OTHER) {
            pre = ExpressionUtils.and(pre, dateExpr.lt(TimeUtils.getDate()));
        }

        String userCode = TokenUtil.getUserCodeFromToken(access_token);
        if (!StringUtils.isEmpty(userCode)) {
            pre = ExpressionUtils.and(pre, QPointsRecord.pointsRecord.user.userCode.eq(userCode));
        }

        return pointsService.findPointsRecords(pre, page);
    }


    @PostMapping({"/rank", "/count"})
    public HttpResponse<PointsListDto> getPointsRanks(@RequestBody PointsReq req) {

        Pageable page = PageRequest.of(req.getPage(), req.getSize(), Sort.Direction.DESC, "totalPointsAmount", "availablePoints");
        Predicate pre = QPoints.points.isNotNull();

        StringTemplate dateExpr = Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", QPoints.points.createDate);
        if (req.getTime() == Time.TODAY) {
            pre = ExpressionUtils.and(pre, dateExpr.eq(TimeUtils.getDate()));
        } else if (req.getTime() == Time.OTHER) {
            pre = ExpressionUtils.and(pre, dateExpr.lt(TimeUtils.getDate()));
        }
        pre = ExpressionUtils.and(pre, QPoints.points.status.eq("0"));

        return pointsService.getPointsRanks(pre, page);
    }

}
