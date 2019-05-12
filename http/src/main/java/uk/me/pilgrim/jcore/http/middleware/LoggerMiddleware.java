package uk.me.pilgrim.jcore.http.middleware;

import uk.me.pilgrim.jcore.http.Request;
import uk.me.pilgrim.jcore.http.Response;

public class LoggerMiddleware implements AutoMiddleware {
    @Override
    public void run(Request req, Response res) {
        System.out.println(req);
    }
}
