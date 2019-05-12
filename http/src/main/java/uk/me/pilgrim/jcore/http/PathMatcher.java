package uk.me.pilgrim.jcore.http;

public class PathMatcher {
    public boolean matches(String pattern, String path) {
        return pattern.equalsIgnoreCase(path);
    }
}
