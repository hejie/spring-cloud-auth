package com.yh.hr.repository;

import com.yh.hr.domain.Goods;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, String>, QuerydslPredicateExecutor<Goods> {

    Goods findByCode(String code);

    @Query(value = "SELECT * FROM yh_hr_goods g WHERE g.id IN(:ids)", nativeQuery = true)
    List<Goods> findGoodsByIds(@Param("ids") List<String> ids);
}
