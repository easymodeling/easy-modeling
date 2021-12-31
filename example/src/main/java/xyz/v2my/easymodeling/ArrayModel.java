package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class ArrayModel {

    public String string;

    public Integer[] intArray;

    public Double[][] matrix;

    public Instant[][][][] crazyInstantArray;

    public int[] primitiveIntArray;

    public int[][] primitiveIntMatrix;
}
