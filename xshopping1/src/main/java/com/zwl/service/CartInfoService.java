package com.zwl.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwl.common.Common;
import com.zwl.entity.CartInfo;
import com.zwl.entity.GoodsInfo;
import com.zwl.entity.OrderInfo;
import com.zwl.entity.UserInfo;
import com.zwl.exception.CustomException;
import com.zwl.mapper.CartInfoMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 购物车相关Service
 */
@Service
public class CartInfoService {

    @Resource
    private CartInfoMapper cartInfoMapper;

    @Resource
    private GoodsInfoService goodsInfoService;

    /**
     * 加入购物车
     */
    public CartInfo add(CartInfo detailInfo) {
        Long userId = detailInfo.getUserid();
        Long goodsId = detailInfo.getGoodsid();
        //查询购物车是否有该商品，有则更新，没有则添加
        Example example = new Example(CartInfo.class);
        example.createCriteria().andEqualTo("userid", userId).andEqualTo("goodsid", goodsId);
        List<CartInfo> infos = cartInfoMapper.selectByExample(example);
        if (CollectionUtil.isEmpty(infos)) {
            //新增
            detailInfo.setCreatetime(DateUtil.formatDateTime(new Date()));
            cartInfoMapper.insertSelective(detailInfo);
        } else {
            //更新
            CartInfo cartInfo = infos.get(0);
            //原来的数量+传过来的数量
            cartInfo.setCount(cartInfo.getCount() + detailInfo.getCount());
            cartInfoMapper.updateByPrimaryKeySelective(cartInfo);
        }
        return detailInfo;
    }


    /**
     * 根据用户id获取购物车商品列表（带购物数量）
     *
     * @param userId
     * @return
     */
    public List<GoodsInfo> findAll(Long userId) {
        List<CartInfo> cartInfoList = cartInfoMapper.findCartByUserId(userId);
        List<GoodsInfo> goodsList = new ArrayList<>();
        for (CartInfo cartInfo : cartInfoList) {
            long goodsId = cartInfo.getGoodsid();
            GoodsInfo goodsInfo = goodsInfoService.findById(goodsId);
            if (goodsInfo != null) {
                //count是用户加入购物车时的商品数量
                goodsInfo.setCount(cartInfo.getCount());
                //id为购物车里的id
                goodsInfo.setId(cartInfo.getGoodsid());
                goodsList.add(goodsInfo);
            }
        }
        return goodsList;
    }

    /**
     * 删除某用户购物车里的某个商品
     */
    public void deleteGoods(Long userId, Long goodsId) {
        cartInfoMapper.deleteGoods(userId, goodsId);
    }

    /**
     * 清空购物车
     */
    public void empty(Long userId) {
        cartInfoMapper.deleteByUserId(userId);
    }

    /**
     * 分页查询购物车列表
     */
    public PageInfo<CartInfo> findPageDetails(Integer pageNum, Integer pageSize, HttpServletRequest request){
        UserInfo user = (UserInfo) request.getSession().getAttribute(Common.USER_INFO);
        if (user == null){
            throw new CustomException("1001","session已失效，请重新登录！");
        }
        Integer level = user.getLevel();//获取用户级别
        PageHelper.startPage(pageNum, pageSize);
        List<CartInfo> list;
        if (level == 1){ //用户级别为 1 : 则代表该用户为管理员 , 用户级别为 2 : 则代表该用户为普通用户
            list = cartInfoMapper.findAll();  //查询所有用户
        } else {
            list = cartInfoMapper.findCartByUserId(user.getId());
        }

        return PageInfo.of(list);
    }

    /**
     * 根据购物车id删除购物车
     */
    public void delete(Long id) {
        cartInfoMapper.deleteByPrimaryKey(id);
    }
}
