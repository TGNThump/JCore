package uk.me.pilgrim.jcore.http.middleware;

import uk.me.pilgrim.jcore.http.Request;
import uk.me.pilgrim.jcore.http.Response;

@FunctionalInterface
public interface Middleware {
    void run(Request req, Response res, Runnable next);
}