package com.example.githubRequester.user;

import com.example.githubRequester.Fixture.UserFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUsersProviderTest {

    @InjectMocks
    DefaultUsersProvider defaultUsersProvider;

    @Mock
    UsersService usersService;

    @Test
    void havingLogin_shouldReturnApiUser() {
        // given
        var userAggregateStub = UserFixture.getUserAggregateStub();
        when(usersService.getUser(anyString())).thenReturn(userAggregateStub);

        // when
        var result = defaultUsersProvider.getUser("anyLogin");

        // then
        assertAll("Should return correctly mapped API user",
                () -> assertEquals(userAggregateStub.getId(), result.getId()),
                () -> assertEquals(userAggregateStub.getLogin(), result.getLogin()),
                () -> assertEquals(userAggregateStub.getName(), result.getName()),
                () -> assertEquals(userAggregateStub.getAvatarUrl(), result.getAvatarUrl()),
                () -> assertEquals(userAggregateStub.getType(), result.getType()),
                () -> assertEquals(userAggregateStub.getCreatedAt(), result.getCreatedAt()),
                () -> assertEquals(userAggregateStub.getCalculations(), result.getCalculations()));
    }
}