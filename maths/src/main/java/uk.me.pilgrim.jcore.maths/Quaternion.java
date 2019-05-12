package uk.me.pilgrim.jcore.maths;

import static java.lang.Math.*;

public class Quaternion {

    private double w;
    private Vec3 v = new Vec3();

    public Quaternion(){}

    public Quaternion(Vec3 axis, double theta){
        theta = toRadians(theta);
        w = cos(theta/2);
        v = axis.multiply(sin(theta/2));

        normalise();
    }

    public double norm(){
        double a = w;
        double b = v.getX();
        double c = v.getY();
        double d = v.getZ();

        return sqrt(a*a+b*b+c*c+d*d);
    }

    public void normalise(){
        scale(1/norm());
    }

    public void scale(double scalar){
        this.w *= scalar;
        Vec.scale(v, scalar, v);
    }
    public Quaternion multiply(Quaternion q){
        Quaternion r = new Quaternion();

        r.w = w*q.w - v.dot(q.v);
        r.v = v.multiply(q.w).add(q.v.multiply(w)).add(v.cross(q.v));

        r.normalise();

        return r;
    }

    public Vec3 mul(Vec3 other){
        Vec3 cross = v.cross(other);
        return other.add(cross.multiply(2*w)).add(v.cross(cross).multiply(2));
    }

    public Quaternion inverse(){
        Quaternion quat = new Quaternion();
        quat.w = w;
        quat.v = v.multiply(-1);
        return quat;
    }

    public Matrix toMatrix(){
        double a = w, b = v.getX(), c = v.getY(), d = v.getZ();
        double aa = a*a, bb = b*b, cc = c*c, dd = d*d;

        return new Matrix(new double[][]{
            {aa+bb-cc-dd, 2*b*c-2*a*d, 2*b*d+2*a*c, 0},
            {2*b*c+2*a*d, aa-bb+cc-dd, 2*c*d-2*a*b, 0},
            {2*b*d-2*a*c, 2*c*d+2*a*b, aa-bb-cc+dd, 0},
            {0,           0,           0,           1}
        });
    }

    public String toString(){
        return String.format("[%.2f, %.2f, %.2f, %.2f]", w, v.getX(), v.getY(), v.getZ());
    }
}
