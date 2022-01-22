package xyz.v2my.easymodeling.randomizer;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalRandomizerTest extends RandomizerTest {

    @Test
    void should_generate_optional_randomly() {
        final boolean allowEmpty = true;
        OptionalRandomizer<Integer> randomizer = new OptionalRandomizer<>(new IntegerRandomizer(1), allowEmpty);

        final Map<Boolean, Long> collect = Stream.generate(randomizer::next).limit(100).collect(Collectors.groupingBy(Optional::isPresent, Collectors.counting()));
        assertThat(collect)
                .containsEntry(true, 67L)
                .containsEntry(false, 33L);
    }

    @RepeatedTest(100)
    void should_generate_non_empty_optional() {
        final boolean allowEmpty = false;
        OptionalRandomizer<Integer> randomizer = new OptionalRandomizer<>(new IntegerRandomizer(1), allowEmpty);

        final Optional<Integer> next = randomizer.next();

        assertThat(next).isNotEmpty();
    }
}
