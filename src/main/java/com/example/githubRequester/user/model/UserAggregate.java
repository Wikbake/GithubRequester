package com.example.githubRequester.user.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class UserAggregate {
    private String id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private String createdAt;
    private float calculations;
    private int publicRepos;
    private int followers;

    private Long requestCount;
    private UUID dbId;

    public static UserAggregate create(ProviderUser providerUser) {
        return UserAggregate.builder()
                .id(providerUser.getId())
                .login(providerUser.getLogin())
                .name(providerUser.getName())
                .type(providerUser.getType())
                .avatarUrl(providerUser.getAvatarUrl())
                .createdAt(providerUser.getCreatedAt())
                .publicRepos(providerUser.getPublicRepos())
                .followers(providerUser.getFollowers())
                .calculations(prepareCalculations(providerUser))
                .build();
    }

    private static float prepareCalculations(ProviderUser providerUser) {
        if (providerUser.getFollowers() == 0 || providerUser.getPublicRepos() == 0) {
            return 0F;
        }
        return (float) 6 / providerUser.getFollowers() * (2 + providerUser.getPublicRepos());
    }
}
