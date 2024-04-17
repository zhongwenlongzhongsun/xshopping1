package com.zwl.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 公告信息表
 */
@Table(name = "advertiser_info")
@Data
public class AdvertiserInfo {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 公告标题
     */
    private String name;

    /**
     * 添加时间
     */
    private String time;

    /**
     * 公告内容
     */
    private String content;

}