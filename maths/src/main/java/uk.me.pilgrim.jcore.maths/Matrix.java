package uk.me.pilgrim.jcore.maths;

import java.text.DecimalFormat;
import java.util.OptionalDouble;

import static java.lang.Math.*;

public class Matrix {

    public static Matrix identitiy(int size){
        double [][] result = new double[size][size];
        for (int i = 0; i < size; i++){
            result[i][i] = 1;
        }
        return new Matrix(result);
    }


    protected final int rows, cols;

    // [rows][cols]
    protected final double[][] values;

    public Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.values = new double[rows][cols];
    }

    public Matrix(double[][] values){
        this.rows = values.length;
        this.cols = values[0].length;
        this.values = values;
    }

    public double unsafeGet(int row, int col){
        return values[row][col];
    }

    public OptionalDouble get(int row, int col){
        if (row >= rows) return OptionalDouble.empty();
        if (col >= cols) return OptionalDouble.empty();
        return OptionalDouble.of(values[row][col]);
    }

    @Override
    public String toString(){
        int[] integerDigits = new int[cols];
        int[] fractionalDigits = new int[cols];
        boolean[] anyNegative = new boolean[cols];

        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                double value = values[row][col];
                if (value < 0) anyNegative[col] = true;

                value = abs(round(value * 1E+3) / 1E+3);

                String[] split = Double.valueOf(value).toString().split("\\.");

                integerDigits[col] = max(integerDigits[col], split[0].length());
                fractionalDigits[col] = max(fractionalDigits[col], split[1].length());
            }
        }

        DecimalFormat df = new DecimalFormat();
        df.setDecimalSeparatorAlwaysShown(true);

        StringBuilder b = new StringBuilder();
        for (int row = 0; row < rows; row++){
            if (row == 0) b.append("┏ ");
            else if (row == rows-1) b.append("┗ ");
            else b.append("┃ ");

            for (int col = 0;; col++) {
                df.setMinimumIntegerDigits(integerDigits[col]);
                df.setMinimumFractionDigits(fractionalDigits[col]);
                df.setPositivePrefix(anyNegative[col] ? " " : "");

                String[] split = df.format(values[row][col]).split("\\.");

                char[] chars = split[0].toCharArray();
                if (split[0].replaceAll("-", "").equals("0")) b.append(split[0]);
                else {
                    boolean leading = true;
                    for (int i = 0; i < chars.length; i++){
                        char c = chars[i];
                        if (c == '0' && leading){
                            b.append(" ");
                        } else {
                            if (c != '-') leading = false;
                            b.append(c);
                        }
                    }
                }

                b.append(".");

                chars = split[1].toCharArray();

                String fractional = "";
                boolean trailing = true;
                for (int i = chars.length-1; i >= 0; i--){
                    char c = chars[i];
                    if (c == '0' && trailing){
                        fractional = ' ' + fractional;
                    } else {
                        trailing = false;
                        fractional = c + fractional;
                    }
                }
                if (fractional.replaceAll(" ", "").length() == 0) fractional = "0" + fractional.substring(1);
                b.append(fractional);

                if (col == cols-1) break;
                b.append(", ");
            }

            if (row == 0) b.append(" ┓");
            else if (row == rows-1) b.append(" ┛");
            else b.append(" ┃");

            if (row < rows-1) b.append("\n");
        }
        return b.toString();
    }
}
