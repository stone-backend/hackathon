package com.hackathon.software.config;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GithubConfig {

    @Value("${github.access-token}")
    private String accessToken;

    @Bean
    public GitHub gitHubClient() throws IOException {
        return  new GitHubBuilder().withOAuthToken(accessToken).build();
    }
}
