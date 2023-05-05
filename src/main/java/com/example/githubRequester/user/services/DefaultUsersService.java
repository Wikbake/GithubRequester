package com.example.githubRequester.user.services;

import com.example.githubRequester.user.HttpClient;
import com.example.githubRequester.user.UsersService;
import com.example.githubRequester.user.exception.UserNotFoundException;
import com.example.githubRequester.user.infrastructure.UsersProperties;
import com.example.githubRequester.user.infrastructure.db.UsersRepository;
import com.example.githubRequester.user.model.ProviderUser;
import com.example.githubRequester.user.model.UserAggregate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUsersService implements UsersService {

    private final HttpClient httpClient;
    private final UsersProperties config;
    private final UsersRepository usersRepository;

    @Override
    public UserAggregate getUser(String login) {
        try {
            var githubUser = callForUser(login);

            return saveUser(githubUser.get()).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public CompletableFuture<ProviderUser> callForUser(String user) throws UserNotFoundException {
        log.info("Looking up " + user);

        var url = UriComponentsBuilder.fromUriString(config.getHost());
        url.pathSegment(config.getUsersPath(), user);

        var githubUser = httpClient.sendGet(url.toUriString(), ProviderUser.class)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with login %s does not exist", user)));

        log.info("Got " + user);

        return CompletableFuture.completedFuture(githubUser);
    }

    @Transactional
    @Async
    public CompletableFuture<UserAggregate> saveUser(ProviderUser githubUser) {
        log.info("Saving user " + githubUser.getLogin());
        var user = UserAggregate.create(githubUser);

        user = usersRepository.save(user);
        log.info("Saved user " + githubUser.getLogin());
        return CompletableFuture.completedFuture(user);
    }
}
