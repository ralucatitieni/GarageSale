package com.portfolio.model.response;

public interface ResponseFactory<T, E> {

    E createResponse(T request);
}
