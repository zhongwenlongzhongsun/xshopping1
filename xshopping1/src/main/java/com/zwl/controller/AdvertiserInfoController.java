package com.zwl.controller;

import com.github.pagehelper.PageInfo;
import com.zwl.common.Result;
import com.zwl.entity.AdvertiserInfo;
import com.zwl.service.AdvertiserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 公告相关的控制器
 */
@RestController
@RequestMapping(value = "/advertiserInfo")
public class AdvertiserInfoController {

    @Resource
    private AdvertiserInfoService advertiserInfoService;

    /**
     * * 显示页码
     * @RequestParam 默认返回第一页
     * @param pageNum 第几页
     * @param pageSize 页面大小为 8
     * @param name 公告名
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<AdvertiserInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "8") Integer pageSize,
                                           @PathVariable String name){
        return Result.success(advertiserInfoService.findPage(pageNum,pageSize,name));
    }

    /**
     * 新增公告
     */
    @PostMapping
    public Result<AdvertiserInfo> add(@RequestBody AdvertiserInfo advertiserInfo){
        advertiserInfoService.add(advertiserInfo);
        return Result.success(advertiserInfo);
    }

    /**
     * 修改公告
     */
    @PutMapping
    public Result update(@RequestBody AdvertiserInfo advertiserInfo){
        advertiserInfoService.update(advertiserInfo);
        return Result.success();
    }

    /**
     * 删除公告
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        advertiserInfoService.delete(id);
        return Result.success();
    }

    /**
     * 根据id查询一条公告
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id){
        AdvertiserInfo advertiserInfo = advertiserInfoService.findById(id);
        return Result.success(advertiserInfo);
    }
}
