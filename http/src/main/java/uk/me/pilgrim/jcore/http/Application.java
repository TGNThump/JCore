package uk.me.pilgrim.jcore.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import uk.me.pilgrim.jcore.http.middleware.AutoMiddleware;
import uk.me.pilgrim.jcore.http.middleware.Middleware;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;

public class Application implements Router {

    private final HttpServer server;
    private PathMatcher pathMatcher = new PathMatcher();
    private List<Middleware> middleware = new ArrayList<>();

    public Application() throws IOException {
        server = HttpServer.create();
        server.createContext("/", this::execute);
        server.setExecutor(Executors.newCachedThreadPool());
    }

    public void use(Middleware middleware){
        this.middleware.add(middleware);
    }

    public void use(AutoMiddleware middleware){
        this.middleware.add((req, res, next) -> {
            middleware.run(req, res);
            next.run();
        });
    }
    @Override
    public void match(EnumSet<Method> methods, String path, Middleware callback) {
        use((req, res, next)-> {
            if (!methods.contains(req.getMethod())){ next.run(); return; }
            if (!pathMatcher.matches(path, req.getPath())){ next.run(); return; }
            callback.run(req, res, next);
        });
    }
    private void execute(HttpExchange t){
        try {
            Request req = new Request(t, this);
            Response res = new Response(t, this);

            Queue<Middleware> middleware = new LinkedList<>();
            middleware.addAll(this.middleware);

            run(middleware, req, res);

            t.sendResponseHeaders(res.getStatus(), res.getBody().getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(res.getBody().getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run(Queue<Middleware> middleware, Request req, Response res){
        if (middleware.isEmpty()) return;
        middleware.poll().run(req, res, () -> {run(middleware, req, res);});
    }

    public void listen(int port){
        try {
            server.bind(new InetSocketAddress(port), 0);
            server.start();
            System.out.println("Listening on port " + port + ".");
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
