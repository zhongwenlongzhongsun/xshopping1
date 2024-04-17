package com.zwl.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Database Table Remarks:
 *   商品评价表
 */
@Data
public class CommentInfo {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *   评价内容
     */
    @Column(name="content")
    private String content;

    /**
     * 所属卖家姓名
     */
    @Transient
    private String userName;

    /**
     * 所属商品名称
     */
    @Transient
    private String goodsName;

    /**
     *   所属商品
     */
    @Column(name="goodsId")
    private Long goodsid;

    /**
     *   评价人id
     */
    @Column(name="userId")
    private Long userid;

    /**
     *   用户等级
     */
    @Column(name="level")
    private Integer level;

    /**
     *   创建时间
     */
    @Column(name="createTime")
    private String createtime;

}