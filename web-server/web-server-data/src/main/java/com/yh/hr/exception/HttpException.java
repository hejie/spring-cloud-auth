package com.yh.hr.exception;

public class HttpException extends BusinessException {


    public HttpException(HttpError error) {
        super(error.code, error.msg);
    }
}