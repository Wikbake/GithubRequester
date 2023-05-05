package com.example.githubRequester.user.infrastructure.db.model;

import com.example.githubRequester.user.model.UserAggregate;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Table
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "github_user")
public class UserEntity {

    @Id
    private UUID id;
    private String login;
    @Column(name = "request_count")
    private Long requestCount;
    @Column(name = "create_ts")
    private LocalDate createTs;
    @Column(name = "modify_ts")
    private LocalDate modifyTs;

    public static UserEntity create(UserAggregate userAggregate) {
        return UserEntity.builder()
                .id(UUID.randomUUID())
                .login(userAggregate.getLogin())
                .requestCount(1L)
                .build();
    }

    public UserAggregate merge(UserAggregate userAggregate) {
        userAggregate.setDbId(this.getId());
        userAggregate.setRequestCount(this.getRequestCount());
        return userAggregate;
    }

    public void bumpRequestCount() {
        this.requestCount++;
    }
}
