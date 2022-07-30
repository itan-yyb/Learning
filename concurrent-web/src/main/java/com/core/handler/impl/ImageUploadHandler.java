package com.core.handler.impl;

import com.core.handler.UploadHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/28
 * 图片处理
 */
@Slf4j
@Service("imageUploadHandler")
public class ImageUploadHandler implements UploadHandler {
    /**
     * 统一上传接口，返回url
     * @param file
     */
    @Override
    public List<String> upload(MultipartFile file) {
        log.info("上传图片处理");
        return null;
    }
}
