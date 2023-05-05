package com.example.githubRequester.async;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AsyncExecutor {

    @Qualifier("githubRequesterTaskExecutor")
    private final TaskExecutor taskExecutor;

    public <T> void executeAsync(Supplier<T> supplier, DeferredResult<T> response) {
        CompletableFuture.supplyAsync(supplier, taskExecutor)
                .thenAccept(response::setResult)
                .exceptionally(ex -> {
                            response.setErrorResult(ex);
                            return null;
                        }
                );
    }
}
