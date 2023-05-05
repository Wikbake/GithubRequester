package com.example.githubRequester.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProviderUser {
    private final String id;
    @NonNull
    private final String login;
    private final String name;
    private final String type;
    @JsonProperty("avatar_url")
    private final String avatarUrl;
    @JsonProperty("created_at")
    private final String createdAt;
    @JsonProperty("public_repos")
    private final int publicRepos;
    private final int followers;
}