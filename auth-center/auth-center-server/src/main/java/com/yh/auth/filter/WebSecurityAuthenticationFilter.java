package com.yh.auth.filter;

import com.yh.common.utils.CookieUtil;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.yh.common.constant.Constants.COOKIE_OPENID;


public class WebSecurityAuthenticationFilter extends ClientCredentialsTokenEndpointFilter {

    private static final String SPRING_SECURITY_RESTFUL_LOGIN_URL = "/oauth/token";

    public WebSecurityAuthenticationFilter() {
        super(SPRING_SECURITY_RESTFUL_LOGIN_URL);
    }

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        Cookie cookie = CookieUtil.get(request, COOKIE_OPENID);
        String openId = null;
        if (cookie != null) {
            openId = cookie.getValue();
        }
//        if (StringUtils.isEmpty(openId)) {
//            redirectStrategy.sendRedirect(request, response, "/api/entry");
//            return null;
//        }

        return super.attemptAuthentication(request, response);
    }

}
