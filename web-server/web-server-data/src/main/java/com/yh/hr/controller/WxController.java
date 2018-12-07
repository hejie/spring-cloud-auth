package com.yh.hr.controller;

import com.yh.common.utils.CookieUtil;
import com.yh.hr.domain.WxUser;
import com.yh.hr.service.UserService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

import static com.yh.hr.utils.Constants.COOKIE_OPENID;
import static me.chanjar.weixin.common.api.WxConsts.OAuth2Scope.SNSAPI_BASE;

@Controller
public class WxController {


    private static final Logger log = LoggerFactory.getLogger(WxController.class);


    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private UserService userService;

    @Value("${api.domain}")
    private String domain;

    /**
     * 微信入口
     */
    @GetMapping("entry")
    public String entry(String code, HttpServletRequest request, HttpServletResponse response) throws WxErrorException {
        Cookie cookie = CookieUtil.get(request, COOKIE_OPENID);
        String openId;
        if (cookie != null) {
            openId = cookie.getValue();
            log.info("openId：{}", openId);
        } else {
            if (StringUtils.isEmpty(code)) {
                String authUrl = wxMpService.oauth2buildAuthorizationUrl(domain + "/api/entry", SNSAPI_BASE, null);
                return "redirect:" + authUrl; // 微信网页授权
            } else {
                WxUser user = userService.isSubscribedByCode(code);
                openId = user.getOpenId();
                CookieUtil.set(response, COOKIE_OPENID, openId, (int) TimeUnit.DAYS.toSeconds(30));
                log.info("用户信息：{}", user);
            }
        }
        if (StringUtils.isNotEmpty(openId)) {
            return "redirect:" + domain;// 进入页面
        } else {
            return null;
        }

    }
}