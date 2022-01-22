package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@AllArgsConstructor
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
}
