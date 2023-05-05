package com.example.githubRequester.user.adapters;

import com.example.githubRequester.adapters.DomainApiAdapter;
import com.example.githubRequester.api.user.model.User;
import com.example.githubRequester.user.model.UserAggregate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserToApiAdapter implements DomainApiAdapter<UserAggregate, User> {

    private static UserToApiAdapter adapter;

    public static UserToApiAdapter of() {
        if (Objects.isNull(adapter)) {
            adapter = new UserToApiAdapter();
        }
        return adapter;
    }

    @Override
    public User adapt(UserAggregate from) {
        return new User(from.getId(),
                from.getLogin(),
                from.getName(),
                from.getType(),
                from.getAvatarUrl(),
                from.getCreatedAt(),
                from.getCalculations());
    }
}
