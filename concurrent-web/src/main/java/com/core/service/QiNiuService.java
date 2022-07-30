package com.core.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import java.io.File;


/**
 * @Author: ye.yanbin
 * @Date: 2022/7/29
 * 七牛云服务接口
 */
public interface QiNiuService {
    /**
     * 上传文件
     * @param file
     * @param objectKey
     * @return 文件地址
     * @throws QiniuException
     */
    String uploadFile(File file, String objectKey) throws QiniuException;

    /**
     * 删除文件
     * @param key
     * @return
     */
    Response delete(String key) throws QiniuException;
}
