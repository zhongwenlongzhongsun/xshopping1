package com.zwl.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 图片文件表
 */
@Table(name = "nx_system_file_info")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginname() {
        return originname;
    }

    public void setOriginname(String originname) {
        this.originname = originname == null ? null : originname.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }
}