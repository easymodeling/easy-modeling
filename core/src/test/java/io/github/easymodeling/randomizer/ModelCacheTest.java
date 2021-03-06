package io.github.easymodeling.randomizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ModelCacheTest {

    private ModelCache cache;

    @BeforeEach
    void setUp() {
        cache = new ModelCache();
    }

    @Nested
    class TestPushModel {

        @Test
        void should_create_pool_and_append_model() {
            cache.push(new BigDecimal(1));

            final Object first = cache.random(BigDecimal.class);

            assertThat(first).isInstanceOf(BigDecimal.class).isEqualTo(new BigDecimal(1));
        }

        @Test
        void should_create_pool_for_different_classes() {
            cache.push(new BigDecimal(1));
            cache.push("abc");

            final Object bigDecimal = cache.random(BigDecimal.class);
            final Object string = cache.random(String.class);

            assertThat(bigDecimal).isInstanceOf(BigDecimal.class).isEqualTo(new BigDecimal(1));
            assertThat(string).isInstanceOf(String.class).isEqualTo("abc");
        }

        @Test
        void should_append_to_existing_pool() {
            cache.push(new BigDecimal(1));
            cache.push(new BigDecimal(2));
            cache.push(new BigDecimal(3));

            final Object first = cache.random(BigDecimal.class);

            assertThat(first).isInstanceOf(BigDecimal.class)
                    .isNotNull()
                    .isIn(new BigDecimal(1), new BigDecimal(2), new BigDecimal(3));
        }
    }

    @Test
    void should_throw_when_request_pool_footer_of_nonexistent_pool() {
        cache.push(new BigDecimal(1));

        final Throwable throwable = catchThrowable(() -> cache.random(String.class));

        assertThat(throwable).isInstanceOf(IllegalStateException.class);
    }

    @Nested
    class TestAvoidInfinityCheck {

        @Test
        void should_not_avoid_infinity_for_non_existent_class() {
            cache.push(new BigDecimal(1));

            final boolean avoidInfinity = cache.avoidInfinity(String.class);

            assertThat(avoidInfinity).isFalse();
        }

        @Test
        void should_not_avoid_infinity_for_pool_not_more_than_4_items_cached() {
            cache.push(new BigDecimal(1));
            cache.push(new BigDecimal(2));
            cache.push(new BigDecimal(3));
            cache.push(new BigDecimal(3));

            final boolean avoidInfinity = cache.avoidInfinity(BigDecimal.class);

            assertThat(avoidInfinity).isFalse();
        }

        @Test
        void should_avoid_infinity_when_pool_is_full() {
            IntStream.range(0, ModelCache.POOL_SIZE)
                    .mapToObj(BigDecimal::new)
                    .forEach(cache::push);

            final boolean avoidInfinity = cache.avoidInfinity(BigDecimal.class);

            assertThat(avoidInfinity).isTrue();
        }
    }
}
