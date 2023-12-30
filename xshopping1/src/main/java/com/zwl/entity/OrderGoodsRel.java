package com.zwl.entity;

import javax.persistence.*;

/**
 *   ������Ʒ��ϵӳ���
 */
@Table(name = "order_goods_rel")
public class OrderGoodsRel {
    /**
     * ����id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *   ����ID
     */
    @Column(name = "orderId")
    private Long orderid;

    /**
     *   ������Ʒ
     */
    @Column(name = "goodsId")
    private Long goodsid;

    /**
     *   ��Ʒ����
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