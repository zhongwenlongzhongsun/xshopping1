package com.zwl.common;
/**
 * 统一返回码
 */

public enum ResultCode {
    Success("0","成功"),
    ERROR("-1","系统异常"),
    PARAM_ERROR("1001","参数已存在"),
    USER_EXIST_ERROR("2001","用户已存在"),
    USER_ACCOUNT_ERROR("2002","账户或密码错误"),
    USER_NOT_EXIST_ERROR("2003","未找到用户"),
    ORDER_PAY_ERROR("3001","库存不足，下单失败");
    public String code;//返回码
    public String msg;//返回中文信息

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
