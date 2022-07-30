package com.core.service.impl;

import com.core.config.QiNiuProperties;
import com.core.service.QiNiuService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;

/**
 * @Author: ye.yanbin
 * @Date: 2022/7/29
 */
@Service
public class QiNiuServiceImpl implements QiNiuService {
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private BucketManager bucketManager;
    @Autowired
    private Auth auth;
    @Autowired
    private QiNiuProperties qiNiuProperties;

    /**
     * 上传文件
     * @param file
     * @param objectKey
     * @return
     * @throws QiniuException
     */
    @Override
    public String uploadFile(File file, String objectKey) throws QiniuException {
        Response response = uploadManager.put(file, objectKey, getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = uploadManager.put(file, objectKey, getUploadToken());
            retry++;
        }
        String url = qiNiuProperties.getUrl();
        url = url.endsWith("/") ? url : url + "/";
        return url + objectKey;
    }

    /**
     * 删除文件
     * @param key
     * @return
     * @throws QiniuException
     */
    @Override
    public Response delete(String key) throws QiniuException {
        Response response = bucketManager.delete(qiNiuProperties.getBucket(), key);
        int retry = 0;
        while (response.needRetry() && retry++ < 3) {
            response = bucketManager.delete(qiNiuProperties.getBucket(), key);
        }
        return response;
    }

    /**
     * 获取上传凭证
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(qiNiuProperties.getBucket(), null, 3600, getStringMap());
    }

    public StringMap getStringMap(){
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
        return putPolicy;
    }
}
