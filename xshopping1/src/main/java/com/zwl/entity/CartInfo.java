package com.zwl.entity;

import lombok.Data;

import javax.persistence.*;

/**
 *   购物车信息表
 */
@Table(name = "cart_info")
@Data
public class CartInfo {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *   所属商品
     */
    @Column(name = "goodsId")
    private Long goodsid;

    /**
     *   数量
     */
    @Column(name = "count")
    private Integer count;

    /**
     *   所属用户
     */
    @Column(name = "userId")
    private Long userid;

    /**
     *   用户等级
     */
    @Column(name = "level")
    private Integer level;

    /**
     *   创建时间
     */
    @Column(name = "createTime")
    private String createtime;

    /**
     * 所属用户名
     * @return
     */
    @Transient
    private String userName;
    /**
     * 商品名称
     * @return
     */
    @Transient
    private String goodsName;

}