package com.yh.api.service;


import com.yh.api.InterfaceService;
import com.yh.api.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@Component
@FeignClient(name = InterfaceService.SERVICE_NAME)
public interface UserFeignClientService {

    @RequestMapping(value = "/user/code/{userCode}", method = RequestMethod.GET)
    User getUserByUserCode(@PathVariable("userCode") String userCode);

    @GetMapping("/user/updateUser")
    void updateUser(@RequestParam("access_Token") String access_Token, @RequestParam("userCode") String userCode, @RequestParam("openId") String openId);
}
