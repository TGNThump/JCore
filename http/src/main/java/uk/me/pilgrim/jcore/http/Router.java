package uk.me.pilgrim.jcore.http;

import uk.me.pilgrim.jcore.http.middleware.AutoMiddleware;
import uk.me.pilgrim.jcore.http.middleware.Middleware;

import java.util.Arrays;
import java.util.EnumSet;

import static uk.me.pilgrim.jcore.http.Method.*;

public interface Router {

    default void get(String path, Middleware callback){
        match(EnumSet.of(GET), path, callback);
    }
    default void post(String path, Middleware callback){
        match(EnumSet.of(POST), path, callback);
    }
    default void put(String path, Middleware callback){
        match(EnumSet.of(PUT), path, callback);
    }
    default void patch(String path, Middleware callback){
        match(EnumSet.of(PATCH), path, callback);
    }
    default void delete(String path, Middleware callback){
        match(EnumSet.of(DELETE), path, callback);
    }
    default void options(String path, Middleware callback){
        match(EnumSet.of(OPTIONS), path, callback);
    }
    default void head(String path, Middleware callback){
        match(EnumSet.of(HEAD), path, callback);
    }
    default void any(String path, Middleware callback){
        match(EnumSet.allOf(Method.class), path, callback);
    }

    default void get(String path, AutoMiddleware callback){
        match(EnumSet.of(GET), path, callback);
    }
    default void post(String path, AutoMiddleware callback){
        match(EnumSet.of(POST), path, callback);
    }
    default void put(String path, AutoMiddleware callback){
        match(EnumSet.of(PUT), path, callback);
    }
    default void patch(String path, AutoMiddleware callback){
        match(EnumSet.of(PATCH), path, callback);
    }
    default void delete(String path, AutoMiddleware callback){
        match(EnumSet.of(DELETE), path, callback);
    }
    default void options(String path, AutoMiddleware callback){
        match(EnumSet.of(OPTIONS), path, callback);
    }
    default void head(String path, AutoMiddleware callback){
        match(EnumSet.of(HEAD), path, callback);
    }
    default void any(String path, AutoMiddleware callback){
        match(EnumSet.allOf(Method.class), path, callback);
    }

    default void match(Method[] methods, String path, AutoMiddleware callback){
        EnumSet<Method> set = EnumSet.noneOf(Method.class);
        set.addAll(Arrays.asList(methods));
        match(set, path, callback);
    }

    default void match(Method[] methods, String path, Middleware callback){
        EnumSet<Method> set = EnumSet.noneOf(Method.class);
        set.addAll(Arrays.asList(methods));
        match(set, path, callback);
    }

    default void match(EnumSet<Method> methods, String path, AutoMiddleware callback){
        match(methods, path, (req, res, next) -> {
            callback.run(req, res);
            next.run();
        });
    }

    void match(EnumSet<Method> methods, String path, Middleware callback);
}
