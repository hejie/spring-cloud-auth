package com.yh.hr.repository;

import com.yh.hr.domain.GoodsCategory;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface GoodsCategoryRepository extends CrudRepository<GoodsCategory, String>, QuerydslPredicateExecutor<GoodsCategory> {

}
