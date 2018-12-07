package com.yh.hr.repository;

import com.yh.hr.domain.Order;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String>, QuerydslPredicateExecutor<Order> {


}
