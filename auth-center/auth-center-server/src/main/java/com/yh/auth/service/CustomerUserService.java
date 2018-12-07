package com.yh.auth.service;

import com.yh.auth.provider.AuthoritiesEnum;
import com.yh.hr.api.service.UserFeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserService implements UserDetailsService {

    @Autowired
    private UserFeignClientService userService;

    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException {
        com.yh.hr.api.pojo.User user = userService.getUserByUserCode(username);
        if (user != null) {
//            String password = passwordEncoder().encode();
            User userInfo = new User(user.getUserCode(),
                    user.getPassword(),
                    AuthorityUtils.commaSeparatedStringToAuthorityList(AuthoritiesEnum.USER.getRole()));
            return userInfo;
        }
        return null;
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
