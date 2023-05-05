package com.example.githubRequester.user.infrastructure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("provider")
public class UsersProperties {

    private String host;
    private String usersPath;
}
