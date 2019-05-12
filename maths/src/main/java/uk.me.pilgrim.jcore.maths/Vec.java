package uk.me.pilgrim.jcore.maths;

import java.util.Arrays;
import java.util.OptionalDouble;

import static java.lang.Math.*;

public class Vec<V extends Vec<V>> {

    private static double epsilon = 1E-6;

    public static Vec create(int size) {
        switch (size){
            case 1: return new Vec1();
            case 2: return new Vec2();
            case 3: return new Vec3();
            case 4: return new Vec4();
        }
        throw new IllegalArgumentException("No Vec" + size + " implementation is available.");
    }

    public static Vec create(double... values){
        switch (values.length){
            case 1: return new Vec1(values[0]);
            case 2: return new Vec2(values[0], values[1]);
            case 3: return new Vec3(values[0], values[1], values[2]);
            case 4: return new Vec4(values[0], values[1], values[2], values[3]);
        }
        throw new IllegalArgumentException("No Vec" + values.length + " implementation is available.");
    }

    //--------------------------------------------------------------------------------------------------------------

    protected final double[] values;
    protected final int size;

    protected Vec(int size){
        this.values = new double[size];
        this.size = size;
    }

    protected Vec(double... values){
        this.values = Arrays.copyOf(values, values.length);
        this.size = values.length;
    }

    public OptionalDouble get(int index){
        if (index > values.length) return OptionalDouble.empty();
        return OptionalDouble.of(values[index]);
    }

    protected double unsafeGet(int index){
        return values[index];
    }

    @Override
    public String toString(){
        return Arrays.toString(values);
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Vec)) return false;

        for (int i = 0; i < max(size, ((Vec) other).size); i++){
            if (abs(get(i).orElse(0) - ((Vec) other).get(i).orElse(0)) > epsilon) return false;
        }

        return true;
    }

    //--------------------------------------------------------------------------------------------------------------

    public static Vec add(Vec a, Vec b){
        return add(a, b, create(max(a.size, b.size)));
    }
    public static Vec add(Vec a, Vec b, Vec dest){
        for (int i = 0; i < dest.size; i++){
            dest.values[i] = a.get(i).orElse(0) + b.get(i).orElse(0);
        }
        return dest;
    }

    public static Vec subtract(Vec a, Vec b){
        return subtract(a, b, create(max(a.size, b.size)));
    }
    public static Vec subtract(Vec a, Vec b, Vec dest){
        for (int i = 0; i < dest.size; i++){
            dest.values[i] = a.get(i).orElse(0) - b.get(i).orElse(0);
        }
        return dest;
    }

    public static Vec multiply(Vec a, Vec b){
        return multiply(a, b, create(max(a.size, b.size)));
    }
    public static Vec multiply(Vec a, Vec b, Vec dest){
        for (int i = 0; i < dest.size; i++){
            dest.values[i] = a.get(i).orElse(0) * b.get(i).orElse(0);
        }
        return dest;
    }

    public static Vec divide(Vec a, Vec b){
        return divide(a, b, create(max(a.size, b.size)));
    }
    public static Vec divide(Vec a, Vec b, Vec dest){
        for (int i = 0; i < dest.size; i++){
            dest.values[i] = a.get(i).orElse(0) / b.get(i).orElse(0);
        }
        return dest;
    }

    public static Vec scale(Vec a, double factor){ return scale(a, factor, create(a.size)); }
    public static Vec scale(Vec a, double factor, Vec dest){
        for (int i = 0; i < a.size; i++){
            dest.values[i] = a.unsafeGet(i) * factor;
        }
        return dest;
    }

    public static double dot(Vec a, Vec b){
        int dims = max(a.size, b.size);
        double dot = 0;

        for (int i = 0; i < dims; i++){
            dot += a.get(i).orElse(0) * b.get(i).orElse(0);
        }

        return dot;
    }

    public static Vec normalize(Vec a){
        double mag2 = a.getMagnitudeSquared();

        if (mag2 > 0 && mag2 != 1) return a.scale(1/sqrt(mag2));
        else return create(a.values);
    }

    //--------------------------------------------------------------------------------------------------------------

    public V add(Vec other) {
        return (V) add(this, other);
    }
    public V subtract(Vec other) {
        return (V) subtract(this, other);
    }
    public V multiply(Vec other) {
        return (V) multiply(this, other);
    }
    public V divide(Vec other) {
        return (V) divide(this, other);
    }

    public V scale(double factor) {
        return (V) scale(this, factor);
    }
    public V multiply(double factor){ return (V) scale(this, factor); }
    public V divide(double factor){ return (V) scale(this, 1/factor); }

    public V normalise(){ return (V) normalize(this); }

    public double dot(Vec other){ return dot(this, other); }
    public double getDistanceTo(Vec other){ return abs(this.subtract(other).getMagnitude()); }
    public double getAngleTo(Vec other){ return acos(dot(other) / getMagnitude() * other.getMagnitude()); }

    //--------------------------------------------------------------------------------------------------------------

    public double getMagnitudeSquared(){
        return dot(this, this);
    }
    public double getMagnitude(){
        return sqrt(getMagnitudeSquared());
    }

    public Matrix toColumnMatrix(){
        Matrix matrix = new Matrix(size, 1);

        for (int i = 0; i < size; i++)
            matrix.values[i][0] = values[i];

        return matrix;
    }

    public Matrix toRowMatrix(){
        Matrix matrix = new Matrix(1, size);
        matrix.values[0] = values;
        return matrix;
    }
}
