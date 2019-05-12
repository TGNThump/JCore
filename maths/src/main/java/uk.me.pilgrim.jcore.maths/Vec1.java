package uk.me.pilgrim.jcore.maths;

public class Vec1 extends Vec<Vec1> {

    public Vec1(){ super(1); }
    public Vec1(double x) {
        super(x);
    }

    public double getX(){ return unsafeGet(0);}

}
