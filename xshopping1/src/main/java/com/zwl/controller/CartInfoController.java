package com.zwl.controller;

import com.github.pagehelper.PageInfo;
import com.zwl.common.Result;
import com.zwl.entity.CartInfo;
import com.zwl.entity.GoodsInfo;
import com.zwl.service.CartInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 购物车相关的控制器
 */
@RestController
@RequestMapping(value = "/cartInfo")
public class CartInfoController {

    @Resource
    private CartInfoService cartInfoService;

    /**
     * 添加购物车
     */
    @PostMapping
    public Result<CartInfo> add(@RequestBody CartInfo cartInfo){
        return Result.success(cartInfoService.add(cartInfo));
    }

    /**
     * 查询用户的购物车(不分页)
     */
    @GetMapping
    public Result<List<GoodsInfo>> findAll(@RequestParam Long userId){
        return Result.success(cartInfoService.findAll(userId));
    }

    /**
     * 删除某用户购物车里的某个商品
     */
    @DeleteMapping("/goods/{userId}/{goodsId}")
    public Result deleteGoods(@PathVariable Long userId,@PathVariable Long goodsId){
        cartInfoService.deleteGoods(userId,goodsId);
        return Result.success();
    }

    /**
     * 根据购物车id删除购物车
     */
    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id){
        cartInfoService.delete(id);
        return Result.success();
    }


    /**
     *   查询购物车列表
     * @param pageNum  第几页
     * @param pageSize 页面大小为 8
     * @return
     */
    @GetMapping("/page")
    public Result<PageInfo<CartInfo>> findPage(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "8") Integer pageSize,
                                                HttpServletRequest request){
        return Result.success(cartInfoService.findPageDetails(pageNum,pageSize,request));
    }
}
