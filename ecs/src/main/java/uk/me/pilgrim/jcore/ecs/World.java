package uk.me.pilgrim.jcore.ecs;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class World<C extends Enum<C>> {

    private int entityId = 0;
    private Map<Integer, EnumSet<C>> entityMap = new ConcurrentHashMap<>();
    private Map<C, Map<Integer, Component>> componentMap = new ConcurrentHashMap<>();

    private final Class<C> enumType;

    public World(Class<C> type){
        this.enumType = type;
    }

    public Integer create(Component... components){
        EnumSet<C> enumSet = EnumSet.noneOf(enumType);

        for (Component c : components){
            C type = C.valueOf(enumType, c.getClass().getSimpleName());
            enumSet.add(type);
            componentMap.putIfAbsent(type, new ConcurrentHashMap<>());
            componentMap.get(type).putIfAbsent(entityId, c);
        }

        entityMap.put(entityId, enumSet);

        return entityId++;
    }

    public Stream<Map<C, Component>> filter(EnumSet<C> mask){
        return entityMap
                .entrySet()
                .parallelStream()
                .filter(es -> es.getValue().containsAll(mask))
                .map(es -> es.getKey())
                .map(id -> {
                    Map<C, Component> components = new HashMap<>();

                    for (C type : mask){
                        components.put(type, componentMap.get(type).get(id));
                    }

                    return components;
                });
    }

    public Stream<Map<C, Component>> filter(C... types){
        EnumSet<C> mask = EnumSet.noneOf(enumType);
        mask.addAll(Set.of(types));
        return filter(mask);
    }
}
