package com.yh.auth.filter;

import com.yh.auth.provider.IRedisService;
import com.yh.common.utils.CookieUtil;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yh.common.constant.Constants.COOKIE_OPENID;


public class LoginAuthenticationFilter extends GenericFilterBean {

    private IRedisService redisService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        String username = request.getParameter("username");

        Cookie cookie = CookieUtil.get(request, COOKIE_OPENID);
        String openId = null;
        if (cookie != null) {
            openId = cookie.getValue();
        }
        if (openId != null) {
            redisService.set(username, openId);
        }

        filterChain.doFilter(request, response);
    }


    public void setRedisService(IRedisService redisService) {
        this.redisService = redisService;
    }
}