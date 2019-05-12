package uk.me.pilgrim.jcore.maths;

public class Vec2 extends Vec<Vec2> {

    public Vec2(){ super(2); }
    public Vec2(double x, double y) {
        super(x,y);
    }

    public double getX(){ return unsafeGet(0);}
    public double getY(){ return unsafeGet(1);}

}
