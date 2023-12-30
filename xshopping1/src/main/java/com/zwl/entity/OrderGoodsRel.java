package com.zwl.entity;

import javax.persistence.*;

/**
 *   订单商品关系映射表
 */
@Table(name = "order_goods_rel")
public class OrderGoodsRel {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *   订单ID
     */
    @Column(name = "orderId")
    private Long orderid;

    /**
     *   所属商品
     */
    @Column(name = "goodsId")
    private Long goodsid;

    /**
     *   商品数量
     */
    @Column(name = "count")
    private Integer count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
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
}