package com.example.githubRequester.user.infrastructure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "http.client")
public class HttpClientProperties {
    private Integer connectionTimeoutInSec;
    private Integer socketTimeoutInSec;
}
