package com.zwl.controller;

import com.github.pagehelper.PageInfo;
import com.zwl.common.Result;
import com.zwl.entity.CommentInfo;
import com.zwl.service.CommentInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品评价相关的控制器
 */
@RestController
@RequestMapping(value = "/commentInfo")
public class CommentInfoController {

    @Resource
    private CommentInfoService commentInfoService;

    @PostMapping
    public Result<CommentInfo> add(@RequestBody CommentInfo commentInfo){
        commentInfoService.add(commentInfo);
        return Result.success(commentInfo);
    }

    /**
     *   查询商品评价列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<CommentInfo>> findPage(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(required = false, defaultValue = "8") Integer pageSize,
                                                  @PathVariable String name){
        return Result.success(commentInfoService.findPages(pageNum,pageSize,name));
    }

    /**
     * 删除一条评论
     */
    @DeleteMapping("/id")
    public Result delete(@PathVariable Long id){
        commentInfoService.delete(id);
        return Result.success();
    }

    /**
     * 根据商品id获取评论列表
     */
    @GetMapping("/all/{goodsid}")
    public Result<List<CommentInfo>> findByGoodsId(@PathVariable Long goodsid){
        return Result.success(commentInfoService.findByGoodsId(goodsid));
    }


}
