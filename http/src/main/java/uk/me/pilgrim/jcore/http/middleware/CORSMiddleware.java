package uk.me.pilgrim.jcore.http.middleware;

import uk.me.pilgrim.jcore.http.Request;
import uk.me.pilgrim.jcore.http.Response;

public class CORSMiddleware implements AutoMiddleware{
    @Override
    public void run(Request req, Response res) {
        String origin = req.getHeaders().getOrDefault("origin", req.getHeaders().get("host")).get(0);
        res.addHeader("Access-Control-Allow-Origin", origin);
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Headers", "content-type");
        res.addHeader("Access-Control-Allow-Headers", "x-apollo-tracing");

    }
}
