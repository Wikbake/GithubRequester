package com.example.githubRequester.Fixture;

import com.example.githubRequester.user.infrastructure.db.model.UserEntity;
import com.example.githubRequester.user.model.ProviderUser;
import com.example.githubRequester.user.model.UserAggregate;

import java.time.LocalDate;
import java.util.UUID;

public class UserFixture {

    public static final String ID_STUB = "IdStub";
    public static final String LOGIN_STUB = "loginStub";
    public static final String NAME_STUB = "nameStub";
    public static final String AVATAR_STUB = "avatarUrlStub";
    public static final String TYPE_STUB = "typeStub";
    public static final int FOLLOWERS_STUB = 100;
    public static final String CREATED_AT_STUB = "2011-01-25T18:44:36Z";
    public static final int PUBLIC_REPOS_STUB = 2000;

    public static UserAggregate getUserAggregateStub() {
        return UserAggregate.builder()
                .id(ID_STUB)
                .login(LOGIN_STUB)
                .name(NAME_STUB)
                .avatarUrl(AVATAR_STUB)
                .type(TYPE_STUB)
                .followers(FOLLOWERS_STUB)
                .createdAt(CREATED_AT_STUB)
                .publicRepos(PUBLIC_REPOS_STUB)
                .build();
    }

    public static UserEntity getUserEntityStub() {
        return UserEntity.builder()
                .id(UUID.randomUUID())
                .requestCount(1L)
                .login(LOGIN_STUB)
                .createTs(LocalDate.now())
                .build();
    }

    public static ProviderUser getProviderUserStub() {
        return new ProviderUser(ID_STUB,
                LOGIN_STUB,
                NAME_STUB,
                TYPE_STUB,
                AVATAR_STUB,
                CREATED_AT_STUB,
                PUBLIC_REPOS_STUB,
                FOLLOWERS_STUB);
    }
}
