package com.yh.hr.controller;

import com.yh.hr.dto.HttpResponse;
import com.yh.hr.exception.HttpError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseController implements ResponseBodyAdvice {

    @Override
    public Object beforeBodyWrite(Object returnValue, MethodParameter methodParameter,
                                  MediaType mediaType, Class c, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (returnValue instanceof HttpResponse) {
            HttpResponse<Object> reply = (HttpResponse<Object>) returnValue;
            if (reply.getCode() != 0) {
                return reply;
            } else {
                reply.setCode(HttpError.ERROR1.code);
                if (StringUtils.isEmpty(reply.getMessage())) {
                    reply.setMessage(HttpError.ERROR1.msg);
                }
            }
            return reply;
        }
        return returnValue;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {

        return true;
    }
}