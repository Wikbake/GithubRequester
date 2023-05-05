package com.example.githubRequester.api.user.model;

import lombok.Data;

@Data
public class User {
    private final String id;
    private final String login;
    private final String name;
    private final String type;
    private final String avatarUrl;
    private final String createdAt;
    private final float calculations;
}
