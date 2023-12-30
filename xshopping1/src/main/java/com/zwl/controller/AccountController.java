package com.zwl.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.zwl.common.Common;
import com.zwl.common.Result;
import com.zwl.common.ResultCode;
import com.zwl.entity.UserInfo;
import com.zwl.exception.CustomException;
import com.zwl.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 成功登录，退出登录控制器
 */
@RestController
public class AccountController {

    @Resource
    private UserInfoService userInfoService;
    //后台登录
    @PostMapping("/login")
    public Result<UserInfo> login(@RequestBody UserInfo userInfo, HttpServletRequest request){

        if(StrUtil.isBlank(userInfo.getName())||StrUtil.isBlank(userInfo.getPassword())){ //用户名或密码输入不正确
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR); //显示账户或密码错误
        }
        //检查数据库中对应的登录账号密码是否正确，放到session中
        UserInfo login = userInfoService.login(userInfo.getName(),userInfo.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute(Common.USER_INFO, login);
        session.setMaxInactiveInterval(60 * 60 * 24); //一天
        return Result.success(login);
    }

    /**
     * 后台重置密码为123456
     */
    @PostMapping("/resetPassword")
    public Result<UserInfo> resetPassword(@RequestBody UserInfo userInfo){
        return Result.success(userInfoService.resetPassword(userInfo.getName()));
    }

    /**
     * 后台退出登录
     */
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request){
        request.getSession().setAttribute(Common.USER_INFO,null);//管理员退出登录时将session设置成null
        return Result.success();
    }

    /**
     *  前端小程序注册
     */
    @PostMapping("/register")
    public Result<UserInfo> register(@RequestBody UserInfo userInfo, HttpServletRequest request){
        if (StrUtil.isBlank(userInfo.getName()) || StrUtil.isBlank(userInfo.getName())){ //用户名或密码输入不正确
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        UserInfo register = userInfoService.add(userInfo);
        HttpSession session = request.getSession();
        session.setAttribute(Common.USER_INFO, register);
        session.setMaxInactiveInterval(60 * 60 * 24);
        return Result.success(register);
    }

    /**
     *  判断前端用户是否登录
     */
    @GetMapping("/auth")
    public Result getAuth(HttpServletRequest request){
        Object user = request.getSession().getAttribute(Common.USER_INFO);//退出之前先获取用户的session信息
        if(user == null){ //查不到
           return Result.error("401","未登录");
        }
        return Result.success(user);
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody UserInfo userInfo, HttpServletRequest request){
        Object user1 = request.getSession().getAttribute(Common.USER_INFO);//退出之前先获取用户的session信息
        if(user1 == null){ //查不到
            return Result.error("401","未登录");
        }
        UserInfo user = (UserInfo)user1;
        //判断新旧密码不相等的错误情况
        String oldPassword = SecureUtil.md5(userInfo.getPassword()); //将旧密码加密
        if (!oldPassword.equals(user.getPassword())){ //与数据库中的密码进行比较
            return Result.error(ResultCode.USER_ACCOUNT_ERROR.code, ResultCode.USER_ACCOUNT_ERROR.msg);
        }
        //判断新旧密码相等的正确情况
        user.setPassword(SecureUtil.md5(userInfo.getNewPassword()));
        userInfoService.update(user); //更新
        //清空session信息, 让用户重新登录
        request.getSession().setAttribute(Common.USER_INFO,null);

        return Result.success();
    }
}
