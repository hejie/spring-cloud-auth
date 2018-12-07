package com.yh.hr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse<T> {

    private int code;
    private String message;
    private T data;


    public static <T> HttpResponse<T> getInstance(int code, String message) {
        return new HttpResponse(code, message, null);
    }

}
