package com.example.githubRequester.adapters;

public interface DomainApiAdapter<T, V> {

    V adapt(T from);
}
