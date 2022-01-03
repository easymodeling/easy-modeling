package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ListModel {

    public List<Integer>[] arrayOfListOfInts;

    public List<Integer> listOfInts;

    public List<List<Integer>> listOfListOfInts;

    public List<List<List<Integer>>> listOfListOfListOfInts;
}
