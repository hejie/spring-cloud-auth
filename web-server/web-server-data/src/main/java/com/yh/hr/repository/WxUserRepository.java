package com.yh.hr.repository;

import com.yh.hr.domain.WxUser;
import org.springframework.data.repository.CrudRepository;

public interface WxUserRepository extends CrudRepository<WxUser, String> {

    WxUser findByOpenId(String openId);

}
