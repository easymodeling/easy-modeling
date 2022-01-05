package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ListModel {

    public List<List<Integer[]>> listOfListOfArrayOfInts;

    public List<List<Integer[]>[]> listOfArrayOfListOfArrayOfInts;

    public List<Integer[]> listOfArrayOfInts;

    public List<Integer[]>[] arrayOfListOfArrayOfInts;

    public List<Integer[][]> listOfMatrixOfInts;

    public List<Integer>[][][] cubeOfListOfInts;

    public List<Integer>[][] matrixOfListOfInts;

    public List<Integer>[] arrayOfListOfInts;

    public List<Integer> listOfInts;

    public List<int[]> listOfPrimitiveInts;

    public List<List<Integer>> listOfListOfInts;

    public List<List<List<Integer>>> listOfListOfListOfInts;
}
