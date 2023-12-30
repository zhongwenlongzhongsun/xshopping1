package com.zwl.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zwl.common.Result;
import com.zwl.entity.NxSystemFileInfo;
import com.zwl.exception.CustomException;
import com.zwl.service.NxSystemFileInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 图片文件类别相关的控制器
 */
@RestController
@RequestMapping(value = "/files")
public class NxSystemFileInfoController {

    private static final String BASE_PATH = System.getProperty("user.dir") + "/src/main/resources/static/file/";

    @Resource
    private NxSystemFileInfoService nxSystemFileInfoService;

    /**
     * 上传图片文件
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        if (originalName == null) {
            return Result.error("1001", "文件名不能为空");
        }
        if (!originalName.contains("png")
                && !originalName.contains("jpg")
                && !originalName.contains("jpeg")
                && !originalName.contains("gif")) {
            return Result.error("1001", "只能上传图片");
        }
        //给文件名加上时间戳
        String fileName = FileUtil.mainName(originalName) + System.currentTimeMillis() + "." + FileUtil.extName(originalName);
        //文件上传
        FileUtil.writeBytes(file.getBytes(), BASE_PATH + fileName);
        //存入数据库并查询出文件id
        NxSystemFileInfo info = new NxSystemFileInfo();
        info.setOriginname(originalName);//设置原始文件名
        info.setFilename(fileName);//设置实际文件名
        NxSystemFileInfo addInfo = nxSystemFileInfoService.add(info);//将文件名添加进去
        if (addInfo != null) {
            return Result.success(addInfo);
        } else{
            return Result.error("1003", "上传失败");
        }
    }

    /**
     * 修改图片文件
     */
    @PutMapping
    public Result update(@RequestBody NxSystemFileInfo nxSystemFileInfo) {
        nxSystemFileInfoService.update(nxSystemFileInfo);
        return Result.success();
    }

    /**
     * 删除图片文件
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        nxSystemFileInfoService.delete(id);
        return Result.success();
    }

    /**
     * 根据id查询一条图片文件
     */
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        NxSystemFileInfo nxSystemFileInfo = nxSystemFileInfoService.findById(id);
        return Result.success(nxSystemFileInfo);
    }

    /**
     * 下载图片文件
     */
    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {
        if (StrUtil.isBlank(id)||"null".equals(id)){
            throw new CustomException("1001","您未上传文件");
        }
        NxSystemFileInfo nxSystemFileInfo = nxSystemFileInfoService.findById(Long.parseLong(id));
        if (nxSystemFileInfo == null){
            throw new CustomException("1001","没找到该文件");
        }
        byte[] bytes = FileUtil.readBytes(BASE_PATH + nxSystemFileInfo.getFilename());
        response.reset();//重置清空
        //设置response的header
        response.addHeader("Content-Disposition","attachment;filename=" +
                URLEncoder.encode(nxSystemFileInfo.getOriginname(),"UTF-8"));
        response.addHeader("Content-Length", "" + bytes.length);
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(bytes);
        toClient.flush();
        toClient.close();
    }
}
