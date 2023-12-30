package com.zwl.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwl.common.ResultCode;
import com.zwl.entity.UserInfo;
import com.zwl.exception.CustomException;
import com.zwl.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员相关Service
 */
@Service
public class UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 登录
     */
    public UserInfo login(String name, String password) {
        //判断数据库是否有该用户
        List<UserInfo> list = userInfoMapper.findByName(name); //根据名字查询所有数据
        if (CollectionUtil.isEmpty(list)) {
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);  //用户不存在
        }
        //判断密码是否正确
        if (!SecureUtil.md5(password).equals(list.get(0).getPassword())) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR); //密码不存在
        }
        return list.get(0);
    }

    /**
     * 重置密码(忘记密码)
     */
    public UserInfo resetPassword(String name) {
        //判断是否有该用户
        List<UserInfo> list = userInfoMapper.findByName(name);
        if (CollectionUtil.isEmpty(list)) {
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);//用户或密码错误
        }
        list.get(0).setPassword(SecureUtil.md5("123456"));
        userInfoMapper.updateByPrimaryKeySelective(list.get(0));
        return list.get(0);
    }

    /**
     * 分页查询用户列表
     */
    public PageInfo<UserInfo> findPage(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfo> list = userInfoMapper.findByName(name);
        return PageInfo.of(list);
    }

    /**
     * 新增用户
     */
    public UserInfo add(UserInfo userInfo) {
        List<UserInfo> list = userInfoMapper.findByName(userInfo.getName());
        if (CollectionUtil.isNotEmpty(list)) {
            return list.get(0);//获取第一个
        }
        if (StrUtil.isBlank(userInfo.getPassword())) {
            //设置默认密码为123456
            userInfo.setPassword(SecureUtil.md5("123456"));
        } else {
            userInfo.setPassword(SecureUtil.md5(userInfo.getPassword()));
        }
        //设置新增用户为买家
        userInfo.setLevel(3);
        userInfoMapper.insertSelective(userInfo);
        return userInfo;
    }

    /**
     * 修改用户
     */
    public void update(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    /**
     * 根据id删除用户
     */
    public void delete(long id) {
        userInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id获取用户
     */
    public UserInfo findById(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 用户总数
     */
    public Integer count(){
        return userInfoMapper.count();
    }
}
