package com.hackathon.software.config;

import com.lark.oapi.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeiShuConfig {

    @Value("${fei-shu.app-id}")
    private String appId;

    @Value("${fei-shu.app-secret}")
    private String appSecret;

    @Bean
    public Client feiShuClient() {
        return Client.newBuilder(appId,appSecret).build();
    }
}
