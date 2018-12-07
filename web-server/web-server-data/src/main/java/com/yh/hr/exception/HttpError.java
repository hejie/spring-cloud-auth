package com.yh.hr.exception;

public enum HttpError {

    ERROR1(0, "请求成功"),
    ERROR2(-1, "请求失败"),
    ERROR40(4000, "员工不存在"),
    ERROR41(4001, "请先关注公众号"),
    ERROR42(4002, "未绑定工号"),
    ERROR43(4003, "身份证号后六位不能为空"),
    ERROR44(4004, "身份证号或者工号不正确"),
    ERROR45(4005, "绑定成功"),
    ERROR46(4006, "Cookie不存在 重新授权"),
    ERROR61(6001, "商品不存在"),
    ERROR62(6002, "有的商品已不存在"),
    ERROR71(7001, "积分不足"),
    ERROR81(8001, "下单失败");

    public int code;
    public String msg;

    HttpError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
