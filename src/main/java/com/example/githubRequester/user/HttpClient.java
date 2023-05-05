package com.example.githubRequester.user;

import lombok.NonNull;

import java.util.Optional;

public interface HttpClient {
    <T> Optional<T> sendGet(@NonNull String url, @NonNull Class<T> userClass);
}
