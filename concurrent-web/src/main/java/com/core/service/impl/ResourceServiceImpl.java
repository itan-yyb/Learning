package com.core.service.impl;

import com.core.handler.UploadHandler;
import com.core.enums.ResourceType;
import com.core.service.ResourceService;
import com.core.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/28
 */
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {
    /**
     * 获取上传资源类型
     * @return
     */
    @Override
    public ResourceType getResourceType(String type) {
        ResourceType resourceType = ResourceType.convert(type.toUpperCase());
        return resourceType;
    }

    /**
     * 上传资源
     * @param file
     * @param resourceType
     */
    @Override
    public List<String> upload(MultipartFile file, ResourceType resourceType) {
        UploadHandler handler = SpringBeanUtil.getBean(resourceType.getUploadHandler(), UploadHandler.class);
        try {
            return handler.upload(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
