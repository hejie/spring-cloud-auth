package com.yh.auth.provider;

import com.yh.auth.filter.WebSecurityAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAt(getWebSecurityAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
    }


    /**
     * 自定义密码验证
     *
     * @return
     */
    @Bean
    public AuthenticationProvider myAuthenticationProvider() {
        AuthenticationProvider provider = new CustomerAuthenticationProvider();
        return provider;
    }

    /**
     * 自定义过滤器
     *
     * @return
     */
    @Bean
    public WebSecurityAuthenticationFilter getWebSecurityAuthenticationFilter() {
        WebSecurityAuthenticationFilter filter = new WebSecurityAuthenticationFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setAuthenticationSuccessHandler(new LoginAuthSuccessHandler());
        filter.setAuthenticationFailureHandler(new ForwardAuthenticationFailureHandler("/login"));
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }
}