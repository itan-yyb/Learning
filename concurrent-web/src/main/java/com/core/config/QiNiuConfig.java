package com.core.config;

import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 〈简述〉<br>
 * 〈七牛配置文件〉
 * @author ye.yanbin
 * @create 2022/7/29
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.servlet.multipart", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(MultipartProperties.class)
public class QiNiuConfig {

    /**
     * 七牛云配置
     */
    @Autowired
    private QiNiuProperties qiNiuProperties;

    private final MultipartProperties multipartProperties;

    public QiNiuConfig(MultipartProperties multipartProperties) {
        this.multipartProperties = multipartProperties;
    }

    /**
     * 华东   Zone.zone0()
     * 华北   Zone.zone1()
     * 华南   Zone.zone2()
     * 北美   Zone.zoneNa0()
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig() {
        //华东
        Integer index = qiNiuProperties.getRegion();
        if(index==null){
            index = 0;
        }
        Region region = null;
        switch (index){
            case 0:
                region = Region.region0();
                break;
            case 1:
                region = Region.region1();
                break;
            case 2:
                region = Region.region2();
                break;
        }
        return new com.qiniu.storage.Configuration(region);
    }
    /**
     * 构建一个七牛上传工具实例
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfig());
    }
    /**
     * 认证信息实例
     *
     * @return
     */
    @Bean
    public Auth auth() {
        return Auth.create(qiNiuProperties.getAccessKey(),
                qiNiuProperties.getSecretKey());
    }
    /**
     * 构建七牛空间管理实例
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }
}
