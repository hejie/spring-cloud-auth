package com.yh.hr.repository;

import com.yh.hr.domain.Points;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PointsRepository extends CrudRepository<Points, String>, QuerydslPredicateExecutor<Points> {

    Points findByUserUserCode(String userCode);

}
