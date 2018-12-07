package com.yh.auth.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class Auth2Exception extends OAuth2Exception {

    public Auth2Exception(String msg) {
        super(msg);
    }
}