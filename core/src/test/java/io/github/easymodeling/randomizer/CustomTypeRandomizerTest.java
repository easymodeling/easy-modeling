package io.github.easymodeling.randomizer;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class CustomTypeRandomizerTest {

    @Test
    void should_create_new_model_when_cache_is_empty() {
        final CustomTypeRandomizer<SomeType> randomizer = new CustomTypeRandomizer<>(new SomeTypeModeler(), new ModelCache());

        final SomeType someType = randomizer.next();

        assertThat(someType).isNotNull();
    }

    @Test
    void should_create_new_model_when_on_enough_model_cached() {
        final ModelCache modelCache = new ModelCache();
        final SomeType someType1 = new SomeType(1);
        final SomeType someType2 = new SomeType(2);
        modelCache.push(someType1);
        modelCache.push(someType2);
        final CustomTypeRandomizer<SomeType> randomizer = new CustomTypeRandomizer<>(new SomeTypeModeler(), modelCache);

        final SomeType someType = randomizer.next();

        assertThat(someType).isNotNull().isNotIn(someType1, someType2);
    }

    @Test
    void should_pick_randomly_from_the_stack_as_next_model_when_enough_cached() {
        final ModelCache modelCache = new ModelCache();
        final List<SomeType> modelList = IntStream.range(0, ModelCache.STACK_SIZE)
                .mapToObj(SomeType::new)
                .collect(Collectors.toList());
        modelList.forEach(modelCache::push);

        final CustomTypeRandomizer<SomeType> randomizer = new CustomTypeRandomizer<>(new SomeTypeModeler(), modelCache);

        final SomeType someType = randomizer.next();

        assertThat(someType).isNotNull().isNotNull().isIn(modelList);
    }
}
