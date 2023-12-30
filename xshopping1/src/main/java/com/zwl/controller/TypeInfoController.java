package com.zwl.controller;

import com.github.pagehelper.PageInfo;
import com.zwl.common.Result;
import com.zwl.entity.TypeInfo;
import com.zwl.service.TypeInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 商品类别相关的控制器
 */
@RestController
@RequestMapping(value = "/typeInfo")
public class TypeInfoController {

    @Resource
    private TypeInfoService typeInfoService;

    /**
     * * 显示页码
     *
     * @param pageNum  第几页
     * @param pageSize 页面大小为 10
     * @param name     商品类别名称
     * @RequestParam 默认返回第一页
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<TypeInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @PathVariable String name) {
        return Result.success(typeInfoService.findPage(pageNum, pageSize, name));
    }

    /**
     * 新增商品类别
     */
    @PostMapping
    public Result<TypeInfo> add(@RequestBody TypeInfo typeInfo) {
        typeInfoService.add(typeInfo);
        return Result.success(typeInfo);
    }

    /**
     * 修改商品类别
     */
    @PutMapping
    public Result update(@RequestBody TypeInfo typeInfo) {
        typeInfoService.update(typeInfo);
        return Result.success();
    }

    /**
     * 删除商品类别
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        typeInfoService.delete(id);
        return Result.success();
    }

    /**
     * 根据id查询一条商品类别
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        TypeInfo typeInfo = typeInfoService.findById(id);
        return Result.success(typeInfo);
    }
}
