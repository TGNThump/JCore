package uk.me.pilgrim.test;

import uk.me.pilgrim.jcore.ecs.Component;
import uk.me.pilgrim.jcore.ecs.Components;
import uk.me.pilgrim.jcore.ecs.World;
import uk.me.pilgrim.test.components.Camera;
import uk.me.pilgrim.test.components.MeshProvider;
import uk.me.pilgrim.test.components.Transform;

import java.util.EnumSet;
import java.util.Map;

public class Main {

    public static void main(String[] args){
        World world = new World(Components.class);

        world.create(new Transform(), new MeshProvider());
        world.create(new Transform(), new Camera());

         run(world);
    }

    public static void run(World world){
        world.filter(Components.Camera, Components.Transform).forEach(e -> {
            Map<Components, Component> entity = (Map) e;
            entity.get(Components.Camera);
        });


        world.filter(EnumSet.of(Components.Camera)).forEach(entity -> {
            System.out.println(((Map)entity).get(Components.Camera));
        });
    }
}