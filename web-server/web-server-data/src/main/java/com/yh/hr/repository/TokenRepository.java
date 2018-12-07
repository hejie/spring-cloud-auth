package com.yh.hr.repository;

import com.yh.hr.domain.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TokenRepository extends CrudRepository<Token, String> {


    Token findByUserId(String userId);
}
