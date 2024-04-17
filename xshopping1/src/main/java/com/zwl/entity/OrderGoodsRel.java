package com.zwl.entity;

import lombok.Data;

import javax.persistence.*;

//������Ʒ��ϵӳ���
@Table(name = "order_goods_rel")
@Data
public class OrderGoodsRel {

    //����ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //����ID
    @Column(name = "orderId")
    private Long orderid;

    //������Ʒ
    @Column(name = "goodsId")
    private Long goodsid;

    //��Ʒ����
    @Column(name = "count")
    private Integer count;

}