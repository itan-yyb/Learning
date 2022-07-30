package com.core.handler.impl;

import com.core.exception.BusinessException;
import com.core.exception.ExceptionEnum;
import com.core.handler.UploadHandler;
import com.core.entity.MResource;
import com.core.service.QiNiuService;
import com.qiniu.common.QiniuException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/28
 */
@Slf4j
@Service("videoUploadHandler")
public class VideoUploadHandler implements UploadHandler {
    @Autowired
    private QiNiuService qiNiuService;

    /**
     * 统一上传
     * @param file
     */
    @Override
    public List<String> upload(MultipartFile file) throws IOException {
        //先将文件保存至本地
        String filename = file.getOriginalFilename();
        String destinationPath = System.getProperty("user.dir") + File.separator + "upload" + File.separator + filename;
        File video = new File(destinationPath);
        if (!video.exists()) {
            video.mkdirs();
        }
        file.transferTo(video);
        FileUtils.forceDeleteOnExit(video);
        //TODO 入库品牌表
        String[] params = filename.split("_");
        Integer fairId = Integer.valueOf(params[0]);
        String firstLevel = params[1];
        String secondLevel = params[2];
        //TODO 上传操作，需要资源入库
        MResource mr = new MResource();
        String qiniuPrefix = "pdt/video/";
        long maxSize = 500000000L;
        if(file.getSize() > maxSize){
            throw new BusinessException(ExceptionEnum.OSS_PUT_VIDEO_SIZE_ERROR.getCode(), ExceptionEnum.OSS_PUT_VIDEO_SIZE_ERROR.getMessage());
        }
        if(!video.exists()){
            throw new BusinessException(ExceptionEnum.OSS_PUT_INVALID_VIDEO.getCode(),ExceptionEnum.OSS_PUT_INVALID_VIDEO.getMessage());
        }
        try {
            String url = qiNiuService.uploadFile(video, qiniuPrefix + filename);
            log.info("视频url：" + url);
            return Arrays.asList(url);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }
}
