package com.hackathon.software.config;

import com.aliyun.cr20181201.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliACRConfig {

    @Value("$ali-yun.access-key-id")
    private String accessKeyId;

    @Value("$ali-yun.access-key-secret")
    private String accessKeySecret;

    @Bean
    Client aliACRClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "cr.cn-hangzhou.aliyuncs.com";
        return new Client(config);
    }
}
