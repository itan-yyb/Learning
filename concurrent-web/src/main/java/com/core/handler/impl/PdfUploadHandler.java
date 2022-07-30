package com.core.handler.impl;

import com.core.handler.UploadHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/28
 */
@Slf4j
@Service("pdfUploadHandler")
public class PdfUploadHandler implements UploadHandler {
    /**
     * 统一上传
     * @param file
     */
    @Override
    public List<String> upload(MultipartFile file) {
        log.info("上传PDF处理");
        return null;
    }
}
