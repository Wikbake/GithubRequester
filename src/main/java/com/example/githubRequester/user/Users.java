package com.example.githubRequester.user;

import com.example.githubRequester.api.user.model.User;

public interface Users {
    User getUser(String login);
}
