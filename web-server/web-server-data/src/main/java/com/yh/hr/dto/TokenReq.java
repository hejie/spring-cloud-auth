package com.yh.hr.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenReq implements Serializable {

    /**
     * token
     */
    private String access_token;

}
