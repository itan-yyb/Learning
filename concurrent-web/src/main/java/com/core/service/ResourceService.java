package com.core.service;

import com.core.enums.ResourceType;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/28
 */
public interface ResourceService {
    /**
     * 获取上传资源类型
     * @return
     */
    ResourceType getResourceType(String type);

    /**
     * 上传资源
     * @param file
     * @param resourceType
     */
    List<String> upload(MultipartFile file, ResourceType resourceType);
}
