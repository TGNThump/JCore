package uk.me.pilgrim.jcore.util;

import java.util.AbstractMap;

public class Pair<K, V> extends AbstractMap.SimpleEntry<K, V> {

    private static final long serialVersionUID = -1070573552096074805L;
    public Pair(K key, V value) {
        super(key, value);
    }

}
