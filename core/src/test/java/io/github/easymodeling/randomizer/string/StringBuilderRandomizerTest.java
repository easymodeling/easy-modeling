package io.github.easymodeling.randomizer.string;

import io.github.easymodeling.randomizer.RandomizerTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class StringBuilderRandomizerTest extends RandomizerTest {

    @Test
    void should_generate_constant_string() {
        final String constantString = "Given_String";
        StringBuilderRandomizer randomizer = new StringBuilderRandomizer(new StringBuilder(constantString));

        final StringBuilder nextString = randomizer.next();

        assertThat(nextString).hasToString(constantString);
    }

    @RepeatedTest(100)
    void should_generate_random_string_with_given_length() {
        StringBuilderRandomizer randomizer = new StringBuilderRandomizer(3, 20, CharSequenceRandomizer.ALPHABETIC);

        final StringBuilder nextString = randomizer.next();

        assertThat(nextString).hasSizeBetween(3, 19);
    }

    @RepeatedTest(100)
    void should_generate_random_string_contains_only_alphabetic() {
        StringBuilderRandomizer randomizer = new StringBuilderRandomizer(3, 20, CharSequenceRandomizer.ALPHANUMERIC);

        final StringBuilder nextString = randomizer.next();

        assertThat(nextString).hasSizeBetween(3, 19).containsPattern("[a-zA-Z]*");
    }

    @RepeatedTest(100)
    void should_generate_random_string_contains_only_digits() {
        StringBuilderRandomizer randomizer = new StringBuilderRandomizer(3, 20, CharSequenceRandomizer.NUMERIC);

        final StringBuilder nextString = randomizer.next();

        assertThat(nextString).hasSizeBetween(3, 19).containsOnlyDigits();
    }

    @ParameterizedTest
    @ValueSource(ints = {CharSequenceRandomizer.ALPHANUMERIC, CharSequenceRandomizer.ALPHABETIC | CharSequenceRandomizer.NUMERIC})
    void should_generate_random_string_contains_only_letter_and_digits(int charSet) {
        StringBuilderRandomizer randomizer = new StringBuilderRandomizer(3, 20, charSet);

        final StringBuilder nextString = randomizer.next();

        assertThat(nextString).hasSizeBetween(3, 19).containsPattern("[a-zA-Z0-9]*");
    }
}
