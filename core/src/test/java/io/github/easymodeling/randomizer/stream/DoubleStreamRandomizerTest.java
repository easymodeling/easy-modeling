package io.github.easymodeling.randomizer.stream;

import io.github.easymodeling.randomizer.RandomizerTest;
import io.github.easymodeling.randomizer.number.DoubleRandomizer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DoubleStreamRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_double_stream() {
        DoubleStreamRandomizer randomizer = new DoubleStreamRandomizer(new DoubleRandomizer(-0.1, 1.1), 2, 7);

        final List<Double> doubles = randomizer.next().boxed().collect(Collectors.toList());

        assertThat(doubles)
                .hasSizeBetween(2, 6)
                .allMatch(i -> i >= -0.1 && i < 1.1);
    }
}
