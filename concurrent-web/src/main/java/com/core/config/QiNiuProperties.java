package com.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 〈简述〉<br>
 * 〈七牛云配置〉
 * @author ye.yanbin
 * @create 2022/7/29
 */
@Component
@Data
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuProperties {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String url;

    private Integer region;
}
