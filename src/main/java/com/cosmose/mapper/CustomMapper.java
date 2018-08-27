package com.cosmose.mapper;

/**
 * Created by damian on 24.08.18.
 */
public interface CustomMapper<S, T> {

    T toDomain(S s);

    S fromDomain(T t);
}
