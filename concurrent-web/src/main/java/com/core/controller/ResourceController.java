package com.core.controller;

import com.core.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/28
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @PostMapping("/upload")
    public List<String> upload(@RequestParam("type") String type, @RequestParam("file") MultipartFile file) {
        List<String> resp = resourceService.upload(file, resourceService.getResourceType(type));
        return resp;
    }
}
