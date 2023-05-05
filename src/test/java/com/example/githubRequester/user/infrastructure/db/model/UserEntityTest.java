package com.example.githubRequester.user.infrastructure.db.model;

import com.example.githubRequester.Fixture.UserFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserEntityTest {

    @Test
    void shouldCreateEntityFromAggregate() {
        //given
        var userAggregateStub = UserFixture.getUserAggregateStub();

        // when
        var result = UserEntity.create(userAggregateStub);

        // then
        assertAll(() -> assertEquals(userAggregateStub.getLogin(), result.getLogin()),
                () -> assertEquals(1L, result.getRequestCount()),
                () -> assertNotNull(result.getId()));
    }

    @Test
    void shouldBumpRequestCounterByOne() {
        // given
        var userEntityStub = UserFixture.getUserEntityStub();

        // when
        userEntityStub.bumpRequestCount();

        // then
        assertEquals(2, userEntityStub.getRequestCount());
    }

}