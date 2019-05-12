package uk.me.pilgrim.jcore.maths;

import static java.lang.Math.tan;
import static java.lang.Math.toRadians;

public final class Transformation {

    private Transformation(){ throw new IllegalStateException("Cannot initialise static class."); }

    private static Matrix orthographic(float left, float right, float bottom, float top, float near, float far){
        return new Matrix(new double[][]{
            {2/(right-left), 0, 0, -(right+left)/(right-left)},
            {0, 2/(top-bottom), 0, -(top+bottom)/(top-bottom)},
            {0, 0, -2/(far-near), -(far+near)/(far-near)},
            {0, 0, 0, 1}
        });
    }

    public static Matrix perspective(float FOV, float aspectRatio, float near, float far){
        double scale = tan(toRadians(FOV/2)) * near;

        double right = aspectRatio * scale;
        double left = -right;
        double top = scale;
        double bottom = -top;

        return new Matrix(new double[][]{
            {(2*near)/(right-left), 0, (right+left)/(right-left), 0},
            {0, (2*near)/(top-bottom), (top+bottom)/(top-bottom), 0},
            {0, 0, - (far+near)/(far-near), -(2*far*near)/(far-near)},
            {0, 0, -1, 0}
        });
    }

    public static Matrix translation(Vec translation){
        Matrix matrix = Matrix.identitiy(translation.size+1);

        for (int i = 0; i < translation.size; i++)
            matrix.values[i][translation.size] = translation.unsafeGet(i);

        return matrix;
    }

    public static Matrix scale(Vec scalar){
        Matrix matrix = Matrix.identitiy(scalar.size+1);

        for (int i = 0; i < scalar.size; i++)
            matrix.values[i][i] = scalar.unsafeGet(i);

        return matrix;
    }
}
