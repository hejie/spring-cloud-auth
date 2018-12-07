package com.yh.hr.exception;

import com.yh.hr.dto.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = HttpException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HttpResponse<String> httpException(HttpException e) throws Exception {
        return HttpResponse.getInstance(HttpError.ERROR2.code, e.getMessage());
    }

}