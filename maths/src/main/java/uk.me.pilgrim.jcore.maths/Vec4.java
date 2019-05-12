package uk.me.pilgrim.jcore.maths;

public class Vec4 extends Vec<Vec4> {

    public Vec4(){ super(4); }
    public Vec4(double x, double y, double z, double w) {
        super(x,y,z,w);
    }

    public double getX(){ return unsafeGet(0); }
    public double getY(){ return unsafeGet(1); }
    public double getZ(){ return unsafeGet(2); }
    public double getW(){ return unsafeGet(3); }

}
