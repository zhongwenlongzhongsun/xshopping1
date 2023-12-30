package com.zwl.common;

/**
 * 统一返回类
 * @param <T>
 */
public class Result<T> {

    private String code;//返回码
    private String msg;//返回中文信息
    private T data;

    public Result(T data) {
        this.data = data;
    }
    public Result() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //不带参数的成功返回
    public static Result success(){
        Result result = new Result<>();
        result.setCode(ResultCode.Success.code);
        result.setMsg(ResultCode.Success.msg);
        return result;
    }

    //带参数的成功返回
    public static<T> Result<T> success(T data){
        Result result = new Result<>(data);
        result.setCode(ResultCode.Success.code);
        result.setMsg(ResultCode.Success.msg);
        return result;
    }

    //不带参数的失败返回
    public static Result error(){
        Result result = new Result<>();
        result.setCode(ResultCode.ERROR.code);
        result.setMsg(ResultCode.ERROR.msg);
        return result;
    }

    //带参数的失败返回
    public static<T> Result error(String code,String msg){
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
