package com.example.githubRequester.user.infrastructure.web;

import com.example.githubRequester.api.user.model.User;
import com.example.githubRequester.async.AsyncExecutor;
import com.example.githubRequester.user.Users;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersController {

    private final Users users;
    private final AsyncExecutor executor;

    @GetMapping("/{login}")
    public DeferredResult<User> getUser(@PathVariable(name = "login") @NotNull String login) {
        var userDeferredResult = new DeferredResult<User>();
        executor.executeAsync(() -> users.getUser(login), userDeferredResult);

        return userDeferredResult;
    }
}
