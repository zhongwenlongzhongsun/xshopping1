package com.zwl.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.zwl.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * controller全局异常拦截处理
 */
@ControllerAdvice(basePackages = "com.zwl.controller")
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();

    //统一异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody  //返回json字符串，相当于controller方法
    public Result error(HttpServletRequest request, Exception e){
        log.error("异常信息",e);
        return Result.error();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody  //返回json字符串，相当于controller方法
    public Result customError(HttpServletRequest request, CustomException e){
        log.error("异常信息",e.getMsg());
        return Result.error(e.getCode(),e.getMsg());//获取到后端错误信息并返回给前端
    }
}
