package uk.me.pilgrim.jcore.maths;

public class Main {
    public static void main(String[] args){
        Matrix mat = Transformation.perspective(90, 16f/9f, 0.1f, 1000);
        System.out.println(mat);
    }
}
