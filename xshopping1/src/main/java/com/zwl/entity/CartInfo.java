package com.zwl.entity;

import javax.persistence.*;

/**
 *   购物车信息表
 */
@Table(name = "cart_info")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Long goodsid) {
        this.goodsid = goodsid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}