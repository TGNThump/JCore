package uk.me.pilgrim.jcore.http;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Request {

    private final HttpExchange exchange;
    private final Application app;

    public Request(HttpExchange exchange, Application app){
        this.exchange = exchange;
        this.app = app;
    }

    public Application getApp(){
        return app;
    }

    public URL getURL(){
        try {
            String path = "http://" + getHeaders().getFirst("host") + getURI();
            return new URL(path);
        } catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        }
    }

    Optional<String> body = null;

    public Optional<String> getBody(){
        if (body == null) {
            body = Optional.empty();
            try (InputStream resource = exchange.getRequestBody()) {
                InputStreamReader isr = new InputStreamReader(resource, StandardCharsets.UTF_8);
                if (isr.ready())
                    body = new BufferedReader(isr).lines().reduce((a, b) -> a + "\n" + b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    public URI getURI(){
        return exchange.getRequestURI();
    }

    public Headers getHeaders(){
        return exchange.getRequestHeaders();
    }

    public Method getMethod(){
        return Method.valueOf(exchange.getRequestMethod());
    }

    public String getPath(){
        return exchange.getRequestURI().getPath();
    }

    public String getParamString(){
        return exchange.getRequestURI().getQuery();
    }

    public Map<String, String> getParams(){
        String paramString = getParamString();
        HashMap<String, String> map = new HashMap();
        if (paramString == null) return map;

        String[] params = paramString.split("&");

        for (String param : params){
            String[] parts = param.split("=");
            map.put(parts[0], parts[1]);
        }

        return map;
    }

    public String toString(){
        return String.format("%s %s", getMethod(), getURL());
    }

    public String getParam(String key) {
        return getParams().get(key);
    }
}
