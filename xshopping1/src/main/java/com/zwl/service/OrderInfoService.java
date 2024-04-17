package com.zwl.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwl.common.Common;
import com.zwl.common.ResultCode;
import com.zwl.entity.GoodsInfo;
import com.zwl.entity.OrderGoodsRel;
import com.zwl.entity.OrderInfo;
import com.zwl.entity.UserInfo;
import com.zwl.exception.CustomException;
import com.zwl.mapper.OrderGoodsRelMapper;
import com.zwl.mapper.OrderInfoMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单相关Service
 */
@Service
public class OrderInfoService {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private GoodsInfoService goodsInfoService;

    @Resource
    private CartInfoService cartInfoService;

    @Resource
    private OrderGoodsRelMapper orderGoodsRelMapper;

    /**
     * 下单(如果有问题，把已经完成的sql回滚)
     */
    @Transactional
    public OrderInfo add(OrderInfo orderInfo) {
        //1.生成订单信息,用户信息放到orderInfo中
        Long userId = orderInfo.getUserid();
        //订单id：用户id + 当前年月日时分 + 4位流水号
        String orderId = userId + DateUtil.format(new Date(), "yyyyMMddHHmm") + RandomUtil.randomNumbers(4);
        orderInfo.setOrderid(orderId);
        //用户相关
        UserInfo userInfo = userInfoService.findById(userId);
        orderInfo.setLinkaddress(userInfo.getAddress());//链接地址
        orderInfo.setLinkman(userInfo.getNickname());//联系人
        orderInfo.setLinkphone(userInfo.getPhone());//联系电话
        //2.保存订单表
        orderInfo.setCreatetime(DateUtil.formatDateTime(new Date()));
        orderInfoMapper.insertSelective(orderInfo);//保存当前订单
        List<OrderInfo> orderInfoList = orderInfoMapper.findByOrderId(orderId);


        //3.查询订单商品列表，遍历
        List<GoodsInfo> goodsList = orderInfo.getGoodsList();
        for (GoodsInfo orderGoodsVO : goodsList) {
            Long goodsId = orderGoodsVO.getId();
            //商品信息
            GoodsInfo goodsDetail = goodsInfoService.findById(goodsId);
            if (goodsDetail == null) { //当前商品不存在
                continue;//继续下一个循环
            }
            //判断并获取商品数量
            Integer orderCount = orderGoodsVO.getCount() != null ? orderGoodsVO.getCount() : 0; //想买多少
            Integer goodsCount = goodsDetail.getCount() != null ? goodsDetail.getCount() : 0; //目前有多少库存
            //4.扣除库存
            if (orderCount > goodsCount) {
                throw new CustomException(ResultCode.ORDER_PAY_ERROR);
            }
            //已有的商品库存 - 用户购买的数量
            goodsDetail.setCount(goodsCount - orderCount);
            //5.增加销量
            int sales = goodsDetail.getSales() != null ? goodsDetail.getSales() : 0;
            //原有销量 + 购买数量 = 当前销量
            goodsDetail.setSales(sales + orderCount);
            goodsInfoService.update(goodsDetail); //更新销量
            //6.商品订单关系表
            OrderGoodsRel orderGoodsRel = new OrderGoodsRel();
            orderGoodsRel.setOrderid(orderInfoList.get(0).getId());
            orderGoodsRel.setGoodsid(goodsId);
            orderGoodsRel.setCount(orderCount);
            orderGoodsRelMapper.insertSelective(orderGoodsRel);
        }
        //7.清空购物车
        cartInfoService.empty(userId);

        return orderInfo;
    }

    /**
     * 根据前端用户id和状态查询订单列表
     */
    public PageInfo<OrderInfo> findFrontPages(Long userId, String status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderInfo> orderInfos;
        if (userId == null) {
            orderInfos = new ArrayList<>();
        } else {
            orderInfos = orderInfoMapper.findByFrontUserId(userId, status);
        }
        //封装后端订单列表数据给前端用户
        for (OrderInfo orderInfo : orderInfos) {
            packOrder(orderInfo);
        }
        return PageInfo.of(orderInfos);
    }

    /**
     * 封装订单用户和商品信息
     */
    public void packOrder(OrderInfo orderInfo) {
        //用户信息
        orderInfo.setUserInfo(userInfoService.findById(orderInfo.getUserid()));//获取userInfo表中的对象
        //商品信息
        Long orderId = orderInfo.getId();
        List<OrderGoodsRel> rels = orderGoodsRelMapper.findByOrderid(orderId);
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        for (OrderGoodsRel rel:rels){
            //根据order_goods_rel表中的goodsId 查询对应goods_info表中对应每一条商品信息
            GoodsInfo goodsInfo = goodsInfoService.findById(rel.getGoodsid());
            if (goodsInfo != null){//查到了
                //这里的count是用户加入商品的数量，不是商品库存
                goodsInfo.setCount(rel.getCount());
                goodsInfoList.add(goodsInfo);//添加进商品列表
            }
        }
        orderInfo.setGoodsList(goodsInfoList);
    }

    /**
     * 改变订单状态
     * id:订单id
     * status:要改变成为的状态
     */
    public void changeStatus(Long id,String status){
        OrderInfo order = orderInfoMapper.findById(id);
        Long userId = order.getUserid();
        UserInfo user = userInfoService.findById(userId);
        if(status.equals("待发货")){
            //付款校验金额
            Double account = user.getAccount();
            Double totalPrice = order.getTotalprice();
            if(account < totalPrice){
                throw new CustomException("-1","账户余额不足");
            }
            //账户余额 - 订单总价格
            user.setAccount(user.getAccount() - order.getTotalprice());
            userInfoService.update(user);
        }
        //已取消状态
        orderInfoMapper.updateStatus(id,status);
    }

    /**
     * 分页查询订单列表
     */
    public PageInfo<OrderInfo> findPages(Long userId, Integer pageNum, Integer pageSize, HttpServletRequest request){
        UserInfo user = (UserInfo) request.getSession().getAttribute(Common.USER_INFO);
        if (user == null){
            throw new CustomException("1001", "session已失效，请重新登录");
        }
        Integer level = user.getLevel();//获取用户级别
        PageHelper.startPage(pageNum, pageSize);
        List<OrderInfo> orderInfos;
        if (1 == level){ //用户级别为 1 : 则代表该用户为管理员 , 用户级别为 2 : 则代表该用户为普通用户
            orderInfos = orderInfoMapper.selectAll();  //查询所有用户
        } else if(userId != null) {
            orderInfos = orderInfoMapper.findByFrontUserId(userId, null); //查询当前普通用户
        } else {
            orderInfos = new ArrayList<>(); //置空
        }

        for (OrderInfo orderInfo: orderInfos){
            packOrder(orderInfo);//封装订单用户和商品信息
        }

        return PageInfo.of(orderInfos);
    }

    /**
     * 删除订单
     */
    @Transactional
    public void delete(Long id) {
        orderInfoMapper.deleteByPrimaryKey(id);
        orderGoodsRelMapper.deleteByOrderid(id);
    }

    /**
     * 根据订单id查询所有商品
     */
    public OrderInfo findById(Long id) {
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(id);
        packOrder(orderInfo);
        return orderInfo;
    }

    /**
     *   统计已完成的总交易额
     */
    public Double totalPrice(){
        return orderInfoMapper.totalPrice();
    }

    /**
     * 统计总销量
     */
    public Integer totalShopping(){
        return orderGoodsRelMapper.totalShopping();
    }

//    /**
//     * 统计总销量
//     */
//    public Integer totalShopping(){
//        return orderGoodsRelMapper.totalShopping();
//    }
//
    /**
     * 分类总销售额
     */
    public List<Map<String,Object>> getTypePrice(){
        return orderInfoMapper.getTypePrice();
    }

    /**
     * 分类总销售量
     */
    public List<Map<String,Object>> getTypeCount(){
        return orderInfoMapper.getTypeCount();
    }
}
