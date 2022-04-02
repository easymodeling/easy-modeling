package io.github.easymodeling.randomizer;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class ModelerTest {

    @Test
    void should_create_model() {
        final SomeType next = SomeTypeModeler.next();

        assertThat(next).isNotNull();
    }

    @Test
    void should_return_cached_model_randomly_of_stack_to_avoid_infinity() {
        final ModelCache modelCache = new ModelCache();
        final List<SomeType> modelList = IntStream.range(0, ModelCache.STACK_SIZE)
                .mapToObj(SomeType::new)
                .collect(Collectors.toList());
        modelList.forEach(modelCache::push);

        final SomeType random = new SomeTypeModeler().next(modelCache);

        assertThat(random).isNotNull().isIn(modelList);
    }
}
