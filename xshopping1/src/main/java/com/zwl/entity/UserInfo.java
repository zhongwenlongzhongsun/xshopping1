package com.zwl.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 用户信息表
 */
@Table(name = "user_info")
@Data
public class UserInfo {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 编号
     */
    private String code;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 身份证
     */
    private String cardid;

    /**
     * 权限等级
     */
    private Integer level;

    /**
     * 余额
     */
    private Double account;

    /**
     * 新密码
     */
    @Transient
    private String newPassword;

}