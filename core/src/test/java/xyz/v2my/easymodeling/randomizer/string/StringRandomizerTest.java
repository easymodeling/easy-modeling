package xyz.v2my.easymodeling.randomizer.string;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.v2my.easymodeling.randomizer.RandomizerTest;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.v2my.easymodeling.randomizer.string.CharSequenceRandomizer.ALPHABETIC;
import static xyz.v2my.easymodeling.randomizer.string.CharSequenceRandomizer.ALPHANUMERIC;
import static xyz.v2my.easymodeling.randomizer.string.CharSequenceRandomizer.NUMERIC;

class StringRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_string_with_given_length() {
        StringRandomizer randomizer = new StringRandomizer(3, 20, ALPHABETIC);

        final String nextString = randomizer.random();

        assertThat(nextString).hasSizeBetween(3, 19);
    }

    @RepeatedTest(100)
    void should_generate_random_string_contains_only_alphabetic() {
        StringRandomizer randomizer = new StringRandomizer(3, 20, ALPHANUMERIC);

        final String nextString = randomizer.random();

        assertThat(nextString).hasSizeBetween(3, 19).containsPattern("[a-zA-Z]*");
    }

    @RepeatedTest(100)
    void should_generate_random_string_contains_only_digits() {
        StringRandomizer randomizer = new StringRandomizer(3, 20, NUMERIC);

        final String nextString = randomizer.random();

        assertThat(nextString).hasSizeBetween(3, 19).containsOnlyDigits();
    }

    @ParameterizedTest
    @ValueSource(ints = {ALPHANUMERIC, ALPHABETIC | NUMERIC})
    void should_generate_random_string_contains_only_letter_and_digits(int charSet) {
        StringRandomizer randomizer = new StringRandomizer(3, 20, charSet);

        final String nextString = randomizer.random();

        assertThat(nextString).hasSizeBetween(3, 19).containsPattern("[a-zA-Z0-9]*");
    }
}
