package com.example.githubRequester.user.infrastructure.db;

import com.example.githubRequester.user.model.UserAggregate;

public interface UsersRepository {

    UserAggregate save(UserAggregate user);
}
