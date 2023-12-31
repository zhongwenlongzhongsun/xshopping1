package com.zwl.mapper;

import com.zwl.entity.GoodsInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 商品相关的查询
 */
@Repository
public interface GoodsInfoMapper extends Mapper<GoodsInfo> {
    //根据商品名称模糊查询,或者根据id查询一个商品
    List<GoodsInfo> findByName(@Param("name") String name,@Param("id") Long id);
    
    //查询所有推荐商品
    @Select("select * from goods_info where recommend = '是'")
    List<GoodsInfo> findRecommendGoods();

    //查询所有热卖商品
    @Select("select * from goods_info order by sales desc")
    List<GoodsInfo> findHotSalesGoods();

    //根据商品类型查询商品列表
    @Select("select * from goods_info where typeId = #{typeId}")
    List<GoodsInfo> findByType(@Param("typeId") Integer typeId);
}