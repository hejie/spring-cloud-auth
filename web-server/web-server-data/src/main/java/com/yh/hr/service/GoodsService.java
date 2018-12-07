package com.yh.hr.service;


import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import com.yh.hr.domain.FileUpload;
import com.yh.hr.domain.Goods;
import com.yh.hr.domain.GoodsCategory;
import com.yh.hr.dto.*;
import com.yh.hr.exception.HttpError;
import com.yh.hr.exception.HttpException;
import com.yh.hr.repository.GoodsCategoryRepository;
import com.yh.hr.repository.GoodsRepository;
import com.yh.hr.transform.FileTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoodsService {


    @Autowired
    private GoodsCategoryRepository goodsCategoryRepository;
    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileTransform transform;

    @Transactional(readOnly = true)
    public Goods findByCode(String code) {
        return goodsRepository.findByCode(code);
    }

    @Transactional(readOnly = true)
    public Goods findById(String id) {
        Optional<Goods> optional = goodsRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new HttpException(HttpError.ERROR61);
        }

    }

    @Transactional(readOnly = true)
    public List<Goods> findGoodsByIds(List<String> ids) {
        List<Goods> goodsList = goodsRepository.findGoodsByIds(ids);
        if (goodsList != null && goodsList.size() == ids.size()) {
            return goodsList;
        } else {
            throw new HttpException(HttpError.ERROR62);
        }

    }


    @Transactional(readOnly = true)
    public HttpResponse<GoodsCategoryListDto> findByPredicate(Predicate pre) {

        HttpResponse<GoodsCategoryListDto> response = new HttpResponse<>();

        Iterable<GoodsCategory> page = goodsCategoryRepository.findAll(pre);

        if (page != null) {
            List<GoodsCategory> list = Lists.newArrayList(page);
            GoodsCategoryListDto info = new GoodsCategoryListDto();
            List<GoodsCategoryDto> categoryDtoList = list.stream().map(p -> tModel(p)).collect(Collectors.toList());
            info.setGoodsCategoryList(categoryDtoList);
            response.setData(info);
            return response;
        }
        return response;
    }


    @Transactional(readOnly = true)
    public HttpResponse<GoodsDto> findGoodsByPredicate(Predicate pre) {

        HttpResponse<GoodsDto> response = new HttpResponse<>();

        Optional<Goods> optional = goodsRepository.findOne(pre);

        if (optional.isPresent()) {
            GoodsDto info = tModel(optional.get());
            response.setData(info);
            return response;
        }
        return response;
    }


    @Transactional(readOnly = true)
    public HttpResponse<GoodsListDto> findByPredicate(Predicate pre, Pageable pageable) {

        HttpResponse<GoodsListDto> response = new HttpResponse<>();

        Iterable<Goods> page = goodsRepository.findAll(pre, pageable);
        if (page != null) {
            List<Goods> list = Lists.newArrayList(page);
            GoodsListDto info = new GoodsListDto();
            List<GoodsDto> goodsDtoList = list.stream().map(p -> tModel(p)).collect(Collectors.toList());
            info.setGoodsList(goodsDtoList);
            response.setData(info);
            return response;
        }
        return response;
    }


    public GoodsCategoryDto tModel(GoodsCategory order) {
        GoodsCategoryDto dto = new GoodsCategoryDto();
        dto.setId(order.getId());
        List<GoodsDto> orderGoodsDtoList = order.getGoodsList().stream().map(p -> tModel(p)).collect(Collectors.toList());
        dto.setGoodsList(orderGoodsDtoList);
        return dto;
    }


    public GoodsDto tModel(Goods p) {
        GoodsDto dto = new GoodsDto();
        dto.setId(p.getId());
        dto.setCode(p.getCode());
        dto.setName(p.getName());
        dto.setPrice(p.getPrice());
        dto.setStock(p.getStock());
        dto.setSales(p.getSales());
        dto.setIntegral(p.getIntegral());
        dto.setUseIntegral(p.isUseIntegral());
        dto.setDescription(p.getDescription());
        dto.setRemarks(p.getRemarks());


        List<FileUpload> listFile = fileService.findByBizKey(p.getId());

        dto.setMainImageId(transform.formItem(listFile));

        dto.setMultiImageId(transform.formList(listFile));

        return dto;
    }

}