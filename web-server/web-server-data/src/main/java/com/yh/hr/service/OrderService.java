package com.yh.hr.service;


import com.querydsl.core.types.Predicate;
import com.yh.hr.domain.*;
import com.yh.hr.dto.*;
import com.yh.hr.exception.HttpError;
import com.yh.hr.exception.HttpException;
import com.yh.hr.repository.OrderRepository;
import com.yh.hr.transform.FileTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileTransform transform;

    @Autowired
    private PointsService pointsService;


    @Transactional(readOnly = true)
    public HttpResponse<OrderListDto> findByPredicate(Predicate pre, Pageable pageable) {

        HttpResponse<OrderListDto> response = new HttpResponse<>();

        Page<Order> page = orderRepository.findAll(pre, pageable);

        if (page != null && page.hasContent()) {
            OrderListDto info = new OrderListDto();
            List<OrderDto> orderDtoList = page.getContent().stream().map(p -> tModel(p)).collect(Collectors.toList());
            CPage p = new CPage();
            p.setTotalPages(page.getTotalPages());
            p.setCurrPage(page.getNumber());
            p.setTotalElements(page.getTotalElements());
            p.setHasNext(page.hasNext());
            info.setPage(p);
            info.setOrderList(orderDtoList);
            response.setData(info);
            return response;
        }

        return response;
    }

    @Transactional(readOnly = true)
    public Order findByOrderId(String orderId) {

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()) {
            return order.get();
        }
        return null;
    }


    public OrderDto tModel(Order order) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getId());
        dto.setDate(order.getCreateDate());
        List<OrderGoodsDto> orderGoodsDtoList = order.getOrderGoodsList().stream().map(p -> tModel(p)).collect(Collectors.toList());
        dto.setOrderGoodsList(orderGoodsDtoList);
        return dto;
    }

    public OrderGoodsDto tModel(OrderGoods p) {
        OrderGoodsDto dto = new OrderGoodsDto();
        dto.setGoodsId(p.getId());
        dto.setPrice(p.getDealPrice());
        dto.setGoodsName(p.getGoodsName());
        dto.setIntegral(p.getIntegral());
        dto.setNum(p.getNum());
        dto.setMainImageId(p.getMainImageId());
        String multiImage = p.getMultiImageId();
        if (!StringUtils.isEmpty(multiImage)) {
            dto.setMultiImageId(Arrays.asList(multiImage.split(",")));
        }
        return dto;
    }

    @Transactional
    public HttpResponse<OrderDto> placeOrder(PlaceOrderReq req, String userCode) {
        User user = userService.findByUserCode(userCode);

        Order order = new Order();
        order.setOpenId(user.getWxOpenid());
        order.setUserCode(user.getUserCode());
        order.setOrderStatus(Order.OrderStatus.WAIT_EXCHANGE);
        List<String> ids = req.getGoods().stream().map(g -> g.getGoodsId()).collect(Collectors.toList());

        List<OrderGoods> goodsList = goodsService.findGoodsByIds(ids).stream().map(g -> {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setGoodsId(g.getId());
            List<FileUpload> listFile = fileService.findByBizKey(g.getId());
            orderGoods.setMainImageId(transform.formItem(listFile));
            List<String> list = transform.formList(listFile);
            if (list != null) {
                String multiImage = list.stream().collect(Collectors.joining(","));
                orderGoods.setMultiImageId(multiImage);
            }
            orderGoods.setSellPrice(g.getPrice());
            orderGoods.setDealPrice(g.getPrice());
            orderGoods.setGoodsName(g.getName());
            orderGoods.setIntegral(g.getIntegral());
            orderGoods.setNum(getNum(req.getGoods(), g.getId()));
            orderGoods.setOrder(order);
            return orderGoods;
        }).collect(Collectors.toList());


        Points points = pointsService.findByUserCode(userCode);
        int subtractPoints = goodsList.stream().mapToInt(OrderGoods::getIntegral).sum();

        int availablePoints = points.getAvailablePoints() - subtractPoints;
        if (availablePoints < 0) {//积分不足
            throw new HttpException(HttpError.ERROR71);
        } else {
            //更新积分
            points.setAvailablePoints(availablePoints);
            pointsService.update(points);
        }

        order.setOrderGoodsList(goodsList);
        orderRepository.save(order);

        if (StringUtils.isEmpty(order.getId())) {
            throw new HttpException(HttpError.ERROR81);
        }


        //积分记录
        PointsRecord record = new PointsRecord();
        record.setUser(user);
        record.setOrderId(order.getId());
        record.setActionType(1);//1 扣减积分
        record.setPoints(subtractPoints);
        record.setRemarks("订单积分");
        pointsService.saveRecord(record);


        HttpResponse<OrderDto> response = new HttpResponse();
        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getId());
        dto.setDate(order.getCreateDate());
        response.setData(dto);

        return response;

    }

    public int getNum(List<PlaceOrderReq.OrderGoodsReq> preGoods, String goodsId) {
        PlaceOrderReq.OrderGoodsReq goods = preGoods.stream().filter(g -> g.getGoodsId().equals(goodsId)).findFirst().orElse(null);
        if (goods != null) {
            return goods.getNum();
        }
        return 0;
    }
}