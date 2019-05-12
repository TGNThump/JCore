package uk.me.pilgrim.jcore.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class Response {

    private final HttpExchange exchange;
    private final Application app;

    private int status = 200;
    private String body = "";
//    private HashMap<String, Set<String>> headers = new HashMap<>();

    public Response(HttpExchange exchange, Application app){
        this.exchange = exchange;
        this.app = app;
    }

    public int getStatus(){
        return status;
    }

    public String getBody(){
        return body;
    }

    public void addHeader(String key, String value){
        if (key == null) throw new IllegalArgumentException("Cannot add null header key");
        if (value == null) throw new IllegalArgumentException("Cannot add null header value");
        exchange.getResponseHeaders().add(key, value);

    }

//    public void addHeader(String key, String value){
//        headers.computeIfAbsent(key, (k) -> new HashSet<>());
//        headers.get(key).add(value);
//    }
//
//    public Map<String, Set<String>> getHeaders(){
//        return headers;
//    }

    public Response status(int status){
        this.status = status;
        return this;
    }

    public Response send(String body){
        this.body = body;
        return this;
    }

    public Response json(String body){
        addHeader("content-type", "application/json");
        this.body = body;
        return this;
    }

    public Response json(Object body){
        addHeader("content-type", "application/json");
        this.body = new Gson().toJson(body);
        return this;
    }

    public Response append(String content){
        this.body += content;
        return this;
    }

}