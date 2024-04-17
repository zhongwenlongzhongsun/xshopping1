package com.zwl.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 商品详情表
 */
@Table(name = "goods_info")
@Data
public class GoodsInfo {
    //@Column：在数据库中的字段
    //@Transient：不在数据库中的字段
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 商品描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 商品价格
     */
    @Column(name = "price")
    private Double price;

    /**
     * 商品折扣
     */
    @Column(name = "discount")
    private Double discount;

    /**
     * 商品销量
     */
    @Column(name = "sales")
    private Integer sales;

    /**
     * 商品点赞数
     */
    @Column(name = "hot")
    private Integer hot;

    /**
     * 是否是推荐
     */
    @Column(name = "recommend")
    private String recommend;

    /**
     * 商品库存
     */
    @Column(name = "count")
    private Integer count;

    /**
     * 所属类别
     */
    @Column(name = "typeId")
    private Long typeid;

    /**
     * 商品图片id，用英文逗号隔开
     */
    @Column(name = "fields")
    private String fields;

    /**
     * 评价人id
     */
    @Column(name = "userid")
    private Long userid;

    /**
     * 用户等级
     */
    @Column(name = "level")
    private Integer level;

    /**
     * 所属类别名称
     */
    @Transient
    private String typeName;

    /**
     * 所属卖家姓名
     */
    @Transient
    private String userName;

    /**
     * 商品图片具体地址列表
     */
    @Transient
    private List<Long> fileList;

    /**
     * 每个已购买的用户评价
     */
    @Transient
    private String commentStatus;

    /**
     * 商品描述
     */
    @Transient
    private String descriptionView;

    public String getDescriptionView() {
        if(StrUtil.isEmpty(description)){
            return "";
        }
        if (description.length() > 35){ //当描述内容大于35个字时，只显示前35个字,后面用...省略
            return description.substring(0,35) + "...";
        }
        return description;
    }

    public void setDescriptionView(String descriptionView) {
        this.descriptionView = descriptionView;
    }
}