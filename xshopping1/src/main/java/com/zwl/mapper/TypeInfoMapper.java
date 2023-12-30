package com.zwl.mapper;

import com.zwl.entity.TypeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 商品类别相关的查询
 */
@Repository
public interface TypeInfoMapper  extends Mapper<TypeInfo> {
    //根据类别名称查询
    List<TypeInfo> findByName(@Param("name") String name);
}