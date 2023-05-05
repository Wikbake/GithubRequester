package com.example.githubRequester.user.infrastructure.web;

import com.example.githubRequester.user.HttpClient;
import com.example.githubRequester.user.infrastructure.HttpClientProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultHttpClient implements HttpClient {

    private final HttpClientProperties config;
    private final ObjectMapper objectMapper;

    @Override
    public <T> Optional<T> sendGet(@NonNull String url, @NonNull Class<T> responseClass) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(config.getConnectionTimeoutInSec() * 1000)
                .setSocketTimeout(config.getSocketTimeoutInSec() * 1000)
                .build();

        final var request = new HttpGet(url);

        try (var httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build()) {
            var response = httpClient.execute(request);
            return response.getStatusLine().getStatusCode() == HttpStatus.OK.value()
                    ? Optional.of(objectMapper.readValue(response.getEntity().getContent(), responseClass))
                    : Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
