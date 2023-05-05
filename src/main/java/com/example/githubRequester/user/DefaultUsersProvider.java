package com.example.githubRequester.user;

import com.example.githubRequester.api.user.model.User;
import com.example.githubRequester.user.adapters.UserToApiAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUsersProvider implements Users {

    private final UsersService service;

    @Override
    public User getUser(String login) {
        return UserToApiAdapter.of().adapt(service.getUser(login));
    }
}
