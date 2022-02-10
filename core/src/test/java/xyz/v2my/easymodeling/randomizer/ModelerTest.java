package xyz.v2my.easymodeling.randomizer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ModelerTest {

    @Test
    void should_create_model() {
        final SomeType next = SomeTypeModeler.next();

        assertThat(next).isNotNull();
    }

    @Test
    void should_return_footer_of_stack_to_avoid_infinity() {
        final ModelCache modelCache = new ModelCache();
        modelCache.push(new SomeType(1));
        modelCache.push(new SomeType(2));
        modelCache.push(new SomeType(3));
        modelCache.push(new SomeType(4));
        modelCache.push(new SomeType(5));

        final SomeType first = new SomeTypeModeler().next(modelCache);

        assertThat(first.anInt).isEqualTo(1);
    }
}
