package com.example.githubRequester.user.infrastructure.db;

import com.example.githubRequester.user.infrastructure.db.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersSpringRepository extends CrudRepository<UserEntity, UUID> {

    Optional<UserEntity> findByLogin(String login);

    boolean existsByLogin(String login);
}
