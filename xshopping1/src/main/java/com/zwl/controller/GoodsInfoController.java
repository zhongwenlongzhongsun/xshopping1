package com.zwl.controller;

import com.github.pagehelper.PageInfo;
import com.zwl.common.Result;
import com.zwl.entity.GoodsInfo;
import com.zwl.service.GoodsInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品相关的控制器
 */
@RestController
@RequestMapping(value = "/goodsInfo")
public class GoodsInfoController {

    @Resource
    private GoodsInfoService goodsInfoService;

    /**
     *  分页查询商品列表
     * @RequestParam 默认返回第一页
     * @param pageNum 第几页
     * @param pageSize 页面大小为 10
     * @param name 商品名称
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<GoodsInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "8") Integer pageSize,
                                           @PathVariable String name){
        return Result.success(goodsInfoService.findPage(pageNum,pageSize,name));
    }

    /**
     * 新增商品
     */
    @PostMapping
    public Result<GoodsInfo> add(@RequestBody GoodsInfo goodsInfo){
        goodsInfoService.add(goodsInfo);
        return Result.success(goodsInfo);
    }

    /**
     * 修改商品
     */
    @PutMapping
    public Result update(@RequestBody GoodsInfo goodsInfo){
        goodsInfoService.update(goodsInfo);
        return Result.success();
    }

    /**
     * 删除商品
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        goodsInfoService.delete(id);
        return Result.success();
    }

    /**
     * 根据id查询一条商品
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id){
        GoodsInfo goodsInfo = goodsInfoService.findById(id);
        return Result.success(goodsInfo);
    }

    /**
     * 获取推荐商品
     */
    @GetMapping("/findRecommendGoods")
    public Result<PageInfo<GoodsInfo>> findRecommendGoods(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "100") Integer pageSize){
        return Result.success(goodsInfoService.findRecommendGoods(pageNum,pageSize));
    }

    /**
     * 获取热卖商品
     */
    @GetMapping("/findHotSalesGoods")
    public Result<PageInfo<GoodsInfo>> findHotSalesGoods(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize){
        return Result.success(goodsInfoService.findHotSalesGoods(pageNum,pageSize));
    }

    /**
     *  根据商品类型查询商品列表
     */
    @GetMapping("/findByType/{typeId}")
    public Result<List<GoodsInfo>> findByType(@PathVariable Integer typeId){
        return Result.success(goodsInfoService.findByType(typeId));
    }
}
