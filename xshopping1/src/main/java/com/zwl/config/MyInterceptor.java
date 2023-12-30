package com.zwl.config;

import com.zwl.common.Common;
import com.zwl.entity.AdvertiserInfo;
import com.zwl.entity.UserInfo;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局拦截器
 * 如果没登陆，重定位到登录页
 */
public class MyInterceptor implements HandlerInterceptor {
    /**
     * 所有访问后台的请求之前先进行全局拦截
     * 判断用户信息是否为空
     * 返回true继续执行后面请求，返回false中断后面请求，直接返回到登录页
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Common.USER_INFO);
        if (userInfo == null){
            response.sendRedirect("/end/page/login.html");
            return false;
        }
        return true;
    }
}
