package com.yh.auth.provider;

import com.yh.auth.exception.InvalidException;
import com.yh.auth.service.CustomerUserService;
import com.yh.common.utils.EncodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;


public class CustomerAuthenticationProvider implements AuthenticationProvider {


    private static final Logger logger = LoggerFactory.getLogger(CustomerAuthenticationProvider.class);

    @Autowired
    private CustomerUserService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        User userInfo = this.userDetailsService.loadUserByUsername(authentication.getName());
        try {
            String credentials = (String) authentication.getCredentials();

            boolean validatePassword = EncodeUtils.validatePassword(credentials, userInfo.getPassword());
            if (validatePassword) {
                logger.info("author success");
                return new UsernamePasswordAuthenticationToken(userInfo, authentication.getCredentials(), userInfo.getAuthorities());
            }
        } catch (Exception e) {
            logger.error("author failed, the error message is: " + e);
            throw e;
        }
        throw new InvalidException("用户名或者密码不匹配", null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
