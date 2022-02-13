package xyz.v2my.easymodeling.randomizer;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EnumRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_create_instance_of_enum() {
        final SomeEnum random = new EnumRandomizer<>(SomeEnum.values()).next();

        assertThat(random).isNotNull().isIn(Arrays.asList(SomeEnum.values()));
    }

    @Test
    void should_roughly_fall_evenly_into_enums() {
        final Map<String, Long> collect = Stream.generate(() -> new EnumRandomizer<>(SomeEnum.values()).next())
                .limit(100)
                .collect(Collectors.groupingBy(SomeEnum::name, Collectors.counting()));

        assertThat(collect.get("A")).isEqualTo(33);
        assertThat(collect.get("B")).isEqualTo(31);
        assertThat(collect.get("C")).isEqualTo(36);
    }

    enum SomeEnum {
        A, B, C
    }

}
