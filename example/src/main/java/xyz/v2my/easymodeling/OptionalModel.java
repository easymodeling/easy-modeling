package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.Optional;

@AllArgsConstructor
public class OptionalModel {

    public Optional<Integer> optionalInteger;

    public Optional<Instant> optionalInstant;

    public Optional<Integer> nullableInteger;

}
