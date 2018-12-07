package com.yh.hr.service;


import com.querydsl.core.types.Predicate;
import com.yh.hr.domain.*;
import com.yh.hr.dto.*;
import com.yh.hr.repository.PointsRecordRepository;
import com.yh.hr.repository.PointsRepository;
import com.yh.hr.transform.FileTransform;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointsService {


    @Autowired
    private PointsRepository pointsRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PointsRecordRepository pointsRecordRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileTransform transform;

    @Transactional(readOnly = true)
    public Points findByUserCode(String userCode) {
        return pointsRepository.findByUserUserCode(userCode);
    }

    @Transactional(readOnly = true)
    public Points findByPredicate(String userCode) {
        return pointsRepository.findByUserUserCode(userCode);
    }


    @Transactional(readOnly = true)
    public HttpResponse<PointsRecordListDto> findPointsRecords(Predicate pre, Pageable pageable) {

        HttpResponse<PointsRecordListDto> response = new HttpResponse<>();

        Page<PointsRecord> page = pointsRecordRepository.findAll(pre, pageable);

        if (page != null && page.hasContent()) {
            PointsRecordListDto info = new PointsRecordListDto();
            List<PointsRecordDto> orderDtoList = page.getContent().stream().map(p -> tPointsModel(p)).collect(Collectors.toList());
            CPage p = new CPage();
            p.setTotalPages(page.getTotalPages());
            p.setCurrPage(page.getNumber());
            p.setTotalElements(page.getTotalElements());
            p.setHasNext(page.hasNext());
            info.setPage(p);
            info.setPointsRecordList(orderDtoList);
            response.setData(info);
            return response;
        }

        return response;
    }

    public PointsRecordDto tPointsModel(PointsRecord points) {
        PointsRecordDto dto = new PointsRecordDto();
        String orderId = points.getOrderId();
        if (StringUtils.isNotEmpty(orderId)) {
            Order order = orderService.findByOrderId(orderId);
            List<OrderGoods> goodsList = order.getOrderGoodsList();
            if (goodsList != null && goodsList.size() > 0) {
                OrderGoods orderGoods = goodsList.get(0);
                dto.setGoodsId(orderGoods.getGoodsId());
                dto.setGoodsName(orderGoods.getGoodsName());
                List<FileUpload> listFile = fileService.findByBizKey(orderGoods.getGoodsId());
                dto.setMainImageId(transform.formItem(listFile));
            }
            dto.setOrderId(order.getId());
        }
        dto.setDate(points.getCreateDate());
        dto.setActionType(points.getActionType());
        dto.setPoints(points.getPoints());
        dto.setRemarks(points.getRemarks());
        return dto;
    }

    @Transactional()
    public HttpResponse<PointsDto> update(Points points) {

        HttpResponse<PointsDto> response = new HttpResponse<>();
        pointsRepository.save(points);

        return response;
    }


    @Transactional()
    public void saveRecord(PointsRecord points) {

        pointsRecordRepository.save(points);
    }


    @Transactional(readOnly = true)
    public HttpResponse<PointsListDto> getPointsRanks(Predicate pre, Pageable pageable) {

        HttpResponse<PointsListDto> response = new HttpResponse<>();

        Page<Points> page = pointsRepository.findAll(pre, pageable);

        if (page != null && page.hasContent()) {
            PointsListDto info = new PointsListDto();
            List<UserPointsDto> orderDtoList = page.getContent().stream().map(p -> tModel(p)).collect(Collectors.toList());
            CPage p = new CPage();
            p.setTotalPages(page.getTotalPages());
            p.setCurrPage(page.getNumber());
            p.setTotalElements(page.getTotalElements());
            p.setHasNext(page.hasNext());
            info.setPage(p);
            info.setUserPointsList(orderDtoList);
            response.setData(info);
            return response;
        }
        return response;
    }


    public UserPointsDto tModel(Points p) {
        UserPointsDto dto = new UserPointsDto();
        User user = p.getUser();
        dto.setUserCode(user.getUserCode());
        dto.setNickName(user.getNickName());
        dto.setAvatar(user.getAvatar());
        dto.setUserName(user.getUserName());
        dto.setAvailablePoints(p.getAvailablePoints());
        dto.setTotalPointsAmount(p.getTotalPointsAmount());
        return dto;
    }

}