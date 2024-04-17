package com.zwl.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 图片文件表
 */
@Table(name = "nx_system_file_info")
@Data
public class NxSystemFileInfo {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 原始文件名
     */
    private String originname;

    /**
     * 存储文件名
     */
    private String filename;

}