package com.example.githubRequester.user.services;

import com.example.githubRequester.Fixture.UserFixture;
import com.example.githubRequester.user.HttpClient;
import com.example.githubRequester.user.exception.UserNotFoundException;
import com.example.githubRequester.user.infrastructure.UsersProperties;
import com.example.githubRequester.user.infrastructure.db.UsersRepository;
import com.example.githubRequester.user.model.UserAggregate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUsersServiceTest {

    @InjectMocks
    DefaultUsersService defaultUsersService;

    @Mock
    HttpClient httpClient;
    @Mock
    UsersProperties usersProperties;
    @Mock
    UsersRepository usersRepository;

    @Test
    void havingLoginPresentInGithub_ReturnUserAggregate() {
        // given
        var providerUserStub = UserFixture.getProviderUserStub();
        when(httpClient.sendGet(anyString(), any())).thenReturn(Optional.of(providerUserStub));

        when(usersProperties.getHost()).thenReturn("hostStub");
        when(usersProperties.getUsersPath()).thenReturn("pathStub");

        var userAggregate = UserAggregate.create(providerUserStub);
        when(usersRepository.save(any(UserAggregate.class))).thenReturn(userAggregate);

        // when
        var result = defaultUsersService.getUser("loginStub");

        // then
        assertAll(() -> assertEquals(providerUserStub.getId(), result.getId()),
                () -> assertEquals(providerUserStub.getLogin(), result.getLogin()),
                () -> assertEquals(providerUserStub.getName(), result.getName()),
                () -> assertEquals(providerUserStub.getType(), result.getType()),
                () -> assertEquals(providerUserStub.getAvatarUrl(), result.getAvatarUrl()),
                () -> assertEquals(providerUserStub.getPublicRepos(), result.getPublicRepos()),
                () -> assertEquals(providerUserStub.getFollowers(), result.getFollowers()),
                () -> assertEquals(120.1199951171875, result.getCalculations())
        );
    }

    @Test
    void havingLoginNotPresentInGithub_ThrowException() {
        // given
        when(httpClient.sendGet(anyString(), any())).thenReturn(Optional.empty());

        when(usersProperties.getHost()).thenReturn("hostStub");
        when(usersProperties.getUsersPath()).thenReturn("pathStub");

        // when && then
        assertThrows(UserNotFoundException.class, () -> defaultUsersService.getUser("loginStub"));
    }
}