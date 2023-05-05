package com.example.githubRequester.e2eTests;

import com.example.githubRequester.api.user.model.User;
import com.example.githubRequester.app.GithubRequesterApplication;
import com.example.githubRequester.user.Users;
import com.example.githubRequester.user.UsersService;
import com.example.githubRequester.user.infrastructure.web.UsersController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("For manual testing only - requires database")
@SpringBootTest(classes = GithubRequesterApplication.class)
class GithubRequesterApplicationE2eTests {

    private static final int THREADS_SIZE = 5;
    private static final int QUEUE_CAPACITY = 100;
    private static final String THREAD_NAME_PREFIX = "GithubRequesterTest-";
    private static final String TESTED_GITHUB_USER = "octocat";

    @Autowired
    UsersController usersController;
    @Autowired
    Users users;
    @Autowired
    UsersService usersService;

    ThreadPoolTaskExecutor executor;

    @BeforeEach
    void setUp() {
        executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(THREADS_SIZE);
        executor.setMaxPoolSize(THREADS_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
    }

    @Test
    void shouldExecuteRequests_andSaveUsersInDb_andReturnThemInConsole() throws ExecutionException, InterruptedException {
        // given
        var githubUsers = new ArrayList<Future<DeferredResult<User>>>();

        // when
        for (var i = 0; i < THREADS_SIZE; i++) {
            githubUsers.add(executor.submit(() -> usersController.getUser(TESTED_GITHUB_USER)));
        }

        // then
        var done = 0;
        while (done != THREADS_SIZE) {
            done = 0;
            for (var future : githubUsers) {
                if (future.isDone() && future.get().hasResult()) {
                    done++;
                }
            }
        }

        for (var com : githubUsers) {
            var result = (User) com.get().getResult();
            Assertions.assertAll("Should contain all fields",
                    () -> assertNotNull(result.getName()),
                    () -> assertNotNull(result.getLogin()),
                    () -> assertNotNull(result.getType()),
                    () -> assertNotNull(result.getCalculations()),
                    () -> assertNotNull(result.getCreatedAt()),
                    () -> assertNotNull(result.getAvatarUrl()));
            System.out.println(result);
        }
    }
}
