package com.zwl.mapper;

import com.zwl.entity.OrderGoodsRel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OrderGoodsRelMapper extends Mapper<OrderGoodsRel> {
    //根据订单id获取商品列表
    @Select("select * from order_goods_rel where orderId = #{orderId}")
    List<OrderGoodsRel> findByOrderid(Long orderId);

    //根据订单删除关联关系
    @Delete("delete from order_goods_rel where orderId = #{orderId}")
    void deleteByOrderid(Long orderId);

    //统计总销量
    @Select("select sum(count) from order_goods_rel")
    Integer totalShopping();
}