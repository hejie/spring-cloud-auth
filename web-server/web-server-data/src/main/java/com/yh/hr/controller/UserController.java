package com.yh.hr.controller;

import com.yh.hr.domain.User;
import com.yh.hr.dto.HttpResponse;
import com.yh.hr.dto.TokenReq;
import com.yh.hr.dto.UserDto;
import com.yh.hr.dto.UserReq;
import com.yh.hr.service.UserService;
import com.yh.hr.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/info")
    public HttpResponse<UserDto> info(@RequestParam("access_token") String access_token) {
        String userCode = TokenUtil.getUserCodeFromToken(access_token);
        return userService.findUserByOpenId(userCode);
    }


    @PostMapping("/register")
    public HttpResponse<TokenReq> register(@RequestBody UserReq user) {
        return userService.update(user);
    }


    @GetMapping("/code/{userCode}")
    public User getUserByUserCode(@PathVariable("userCode") String userCode) {
        return userService.findByUserCode(userCode);
    }
}
