package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.Optional;

@AllArgsConstructor
public class OptionalModel {

    public Optional<Integer> optionalInteger;

    public Optional<Instant> optionalInstant;

    public Optional<Integer> nullableInteger;

    public Optional<Optional<Instant>> optionalOfOptionalInstant;

    public Optional<Instant[]> optionalOfInstantArray;

    public Optional<Instant[][]> optionalOfInstantMatrix;

    public Optional<Instant>[] arrayOfOptionalInstant;

    public Optional<Instant>[][] matrixOfOptionalInstant;

    public Optional<Instant[]>[] arrayOfOptionalArrayOfInstant;

    public Optional<Instant[][]>[] arrayOfOptionalMatrixOfInstant;
}
