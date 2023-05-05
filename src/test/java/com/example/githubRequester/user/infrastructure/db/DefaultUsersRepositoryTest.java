package com.example.githubRequester.user.infrastructure.db;

import com.example.githubRequester.Fixture.UserFixture;
import com.example.githubRequester.user.infrastructure.db.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUsersRepositoryTest {

    @InjectMocks
    DefaultUsersRepository defaultUsersRepository;

    @Mock
    UsersSpringRepository usersSpringRepository;

    @Test
    void shouldSaveUserEntityWithRequestCountOne() {
        // given
        when(usersSpringRepository.existsByLogin(anyString())).thenReturn(false);
        var userEntityStub = UserFixture.getUserEntityStub();
        when(usersSpringRepository.save(any(UserEntity.class))).thenReturn(userEntityStub);

        var userAggregateStub = UserFixture.getUserAggregateStub();

        // when
        var result = defaultUsersRepository.save(userAggregateStub);

        // then
        assertAll(() -> assertEquals(1L, result.getRequestCount()),
                () -> assertEquals(userEntityStub.getId(), result.getDbId()));
    }

    @Test
    void shouldBumpRequestCountAndSaveUserEntity() {
        when(usersSpringRepository.existsByLogin(anyString())).thenReturn(true);
        var userEntityStub = UserFixture.getUserEntityStub();
        when(usersSpringRepository.findByLogin(anyString())).thenReturn(Optional.of(userEntityStub));
        when(usersSpringRepository.save(any(UserEntity.class))).thenReturn(userEntityStub);

        var userAggregateStub = UserFixture.getUserAggregateStub();

        // when
        var result = defaultUsersRepository.save(userAggregateStub);

        // then
        assertAll(() -> assertEquals(2L, result.getRequestCount()),
                () -> assertEquals(userEntityStub.getId(), result.getDbId()));
    }

}