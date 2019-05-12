package uk.me.pilgrim.jcore.maths;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VecTests {

    Vec3 a = new Vec3(1, 2, 3);
    Vec3 b = new Vec3(3, 2, 1);

    @Test
    void create(){
        assertNotNull(Vec.create(1));
        assertNotNull(Vec.create(2));
        assertNotNull(Vec.create(3));
        assertNotNull(Vec.create(4));
        assertNotNull(Vec.create(1d));
        assertNotNull(Vec.create(1d, 1d));
        assertNotNull(Vec.create(1d, 1d, 1d));
        assertNotNull(Vec.create(1d, 1d, 1d));
    }

    @Test
    void get(){
        assertEquals(1, a.getX());
    }

    @Test
    void addition(){
        assertEquals(new Vec3(4,4,4), a.add(b));
    }

    @Test
    void subtract(){
        assertEquals(new Vec3(-2,0,2), a.subtract(b));
    }

    @Test
    void multiply(){
        assertEquals(new Vec3(3, 4, 3), a.multiply(b));
    }

    @Test
    void divide(){
        Vec3 a = new Vec3(9, 18, 24);
        Vec3 b = new Vec3(3,3,3);

        assertEquals(new Vec3(3, 6, 8), a.divide(b));
    }

    @Test
    void dot(){
        assertEquals(10, a.dot(b));
    }

    @Test
    void cross(){
        assertEquals(new Vec3(-4.0, 8.0, -4.0), a.cross(b));
    }

    @Test
    void magnitude(){
        Vec3 a = new Vec3(1, 1, 1);

        assertEquals(Math.sqrt(3), a.getMagnitude());
    }

    @Test
    void normalise(){
        assertEquals(new Vec3(1,1,1).normalise(), new Vec3(3,3,3).normalise());
    }

    @Test
    void scale(){
        assertEquals(new Vec3(2, 4, 6), a.scale(2));
        assertEquals(new Vec3(2, 4, 6), a.multiply(2));
        assertEquals(a, new Vec3(2, 4, 6).divide(2));
    }

    @Test
    void equals(){
        assertEquals(new Vec3(0,0,0), new Vec3(1E-6, 1E-6, 1E-6));
        assertNotEquals(new Vec3(0,0,0), new Vec3(0.1, 0.1, 0.1));
    }
}
