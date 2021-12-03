package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EasyModelingTest {

    @Test
    void should_do_test() {
        final String generated = EasyModeling.generate(String.class);

        assertThat(generated).isNotNull();
    }
}
