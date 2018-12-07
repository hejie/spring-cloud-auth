package com.yh.hr.repository;

import com.yh.hr.domain.PointsRecord;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PointsRecordRepository extends CrudRepository<PointsRecord, String>, QuerydslPredicateExecutor<PointsRecord> {


}
