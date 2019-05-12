package uk.me.pilgrim.jcore.maths;

public class Vec3 extends Vec<Vec3> {

    public Vec3(){ super(3); }
    public Vec3(double x, double y, double z) {
        super(x,y,z);
    }

    public double getX(){ return unsafeGet(0); }
    public double getY(){ return unsafeGet(1); }
    public double getZ(){ return unsafeGet(2); }

    public Vec3 cross(Vec3 other){
        Vec3 result = new Vec3();
        result.values[0] = (getY()*other.getZ() - getZ()*other.getY());
        result.values[1] = (getZ()*other.getX() - getX()*other.getZ());
        result.values[2] = (getX()*other.getY() - getY()*other.getX());
        return result;
    }

}
