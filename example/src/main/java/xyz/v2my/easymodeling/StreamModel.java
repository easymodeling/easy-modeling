package xyz.v2my.easymodeling;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamModel {

    public Stream<String> streamOfStrings;

    public IntStream intStream;

    public Stream<IntStream> streamOfIntStreams;

    public IntStream[] intStreamArray;

    public IntStream[][] intStreamMatrix;

    public LongStream longStream;

    public Stream<LongStream> streamOfLongStreams;

    public LongStream[] longStreamArray;

    public LongStream[][] longStreamMatrix;

    public DoubleStream doubleStream;

    public Stream<DoubleStream> streamOfDoubleStreams;

    public DoubleStream[] doubleStreamArray;

    public DoubleStream[][] doubleStreamMatrix;
}
