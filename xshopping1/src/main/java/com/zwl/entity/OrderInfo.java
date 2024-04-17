package com.zwl.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 *   订单信息表
 */
@Table(name = "order_info")
@Data
public class OrderInfo {
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
    private String orderid;

    /**
     *   总价格
     */
    @Column(name = "totalPrice")
    private Double totalprice;

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
     *   联系地址
     */
    @Column(name = "linkAddress")
    private String linkaddress;

    /**
     *   联系电话
     */
    @Column(name = "linkPhone")
    private String linkphone;

    /**
     *   联系人
     */
    @Column(name = "linkMan")
    private String linkman;

    /**
     *   创建时间
     */
    @Column(name = "createTime")
    private String createtime;

    /**
     *   订单状态
     */
    @Column(name = "status")
    private String status;

    @Transient
    private UserInfo userInfo;

    @Transient
    private List<GoodsInfo> goodsList;

    public String getLinkaddress() {
        return linkaddress;
    }

    public void setLinkaddress(String linkaddress) {
        this.linkaddress = linkaddress == null ? null : linkaddress.trim();
    }

}