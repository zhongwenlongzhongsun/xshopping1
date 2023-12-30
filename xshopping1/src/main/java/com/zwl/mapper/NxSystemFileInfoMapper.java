package com.zwl.mapper;

import com.zwl.entity.NxSystemFileInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 文件相关的查询
 */
@Repository
public interface NxSystemFileInfoMapper extends Mapper<NxSystemFileInfo> {

}