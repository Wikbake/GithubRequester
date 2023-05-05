package com.example.githubRequester.user;

import com.example.githubRequester.user.model.UserAggregate;

public interface UsersService {
    UserAggregate getUser(String login);
}
