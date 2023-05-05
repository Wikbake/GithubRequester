package com.example.githubRequester.user.infrastructure.db;

import com.example.githubRequester.user.infrastructure.db.model.UserEntity;
import com.example.githubRequester.user.model.UserAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultUsersRepository implements UsersRepository {

    private final UsersSpringRepository repository;

    @Override
    public synchronized UserAggregate save(UserAggregate userAggregate) {
        UserEntity userEntity;
        if (repository.existsByLogin(userAggregate.getLogin())) {
            userEntity = repository.findByLogin(userAggregate.getLogin()).get();
            userEntity.bumpRequestCount();
        } else {
            userEntity = UserEntity.create(userAggregate);
        }

        log.info(String.format("About to save user %s with request count %s",
                userEntity.getLogin(),
                userEntity.getRequestCount()));

        userEntity = repository.save(userEntity);
        return userEntity.merge(userAggregate);
    }
}
