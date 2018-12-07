package com.yh.hr.repository;

import com.yh.hr.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByWxOpenid(String openId);

    User findByUserCode(String userCode);
}
