package com.zwl.entity;

import lombok.Data;

import javax.persistence.*;

//订单商品关系映射表
@Table(name = "order_goods_rel")
@Data
public class OrderGoodsRel {

    //自增ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //订单ID
    @Column(name = "orderId")
    private Long orderid;

    //所属商品
    @Column(name = "goodsId")
    private Long goodsid;

    //商品数量
    @Column(name = "count")
    private Integer count;

}