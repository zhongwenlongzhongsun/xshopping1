package com.zwl.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwl.entity.NxSystemFileInfo;
import com.zwl.mapper.NxSystemFileInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 图片文件相关Service
 */
@Service
public class NxSystemFileInfoService {

    @Resource
    private NxSystemFileInfoMapper nxSystemFileInfoMapper;

    /**
     * 新增图片文件
     */
    public NxSystemFileInfo add(NxSystemFileInfo nxSystemFileInfo) {
        nxSystemFileInfoMapper.insertSelective(nxSystemFileInfo);
        return nxSystemFileInfo;
    }

    /**
     * 修改图片文件
     */
    public void update(NxSystemFileInfo nxSystemFileInfo) {
        nxSystemFileInfoMapper.updateByPrimaryKeySelective(nxSystemFileInfo);
    }

    /**
     * 根据id删除图片文件
     */
    public void delete(long id) {
        nxSystemFileInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id获取一个图片文件
     */
    public NxSystemFileInfo findById(Long id) {
        return nxSystemFileInfoMapper.selectByPrimaryKey(id);
    }
}
