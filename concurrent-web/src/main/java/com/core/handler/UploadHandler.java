package com.core.handler;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/28
 * 资源处理接口
 */
public interface UploadHandler {
    /**
     * 统一上传接口，返回url
     * @param file
     */
    List<String> upload(MultipartFile file) throws IOException;

    /**
     * 删除资源
     */
}
