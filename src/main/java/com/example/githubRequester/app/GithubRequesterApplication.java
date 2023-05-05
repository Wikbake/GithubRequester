package com.example.githubRequester.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaRepositories("com.example.githubRequester.user.infrastructure.db")
@EntityScan("com.example.githubRequester.user.infrastructure.db")
@SpringBootApplication(scanBasePackages = "com.example.githubRequester")
@EnableAsync
public class GithubRequesterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubRequesterApplication.class, args);
    }
}
