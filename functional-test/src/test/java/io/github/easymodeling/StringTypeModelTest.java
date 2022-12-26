package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class StringTypeModelTest {

    private final StringTypeModel model = StringTypeModelModeler.next();

    @Test
    void getCommonString() {
        final String commonString = model.getCommonString();

        assertThat(commonString).hasSizeGreaterThanOrEqualTo(10).hasSizeLessThan(20);
    }

    @Test
    void getAlphanumericString() {
        Pattern alphanumericPattern = Pattern.compile("^[a-zA-Z0-9]+$");

        assertThat(model.getAlphanumericString()).containsPattern(alphanumericPattern);
    }

    @Test
    void getAlphabeticString() {
        Pattern alphabeticPattern = Pattern.compile("^[a-zA-Z]+$");

        assertThat(model.getAlphabeticString()).containsPattern(alphabeticPattern);
    }

    @Test
    void getNumericString() {
        Pattern numericPattern = Pattern.compile("^[0-9]+$");

        assertThat(model.getNumericString()).containsPattern(numericPattern);
    }

    @Test
    void getConstString() {
        assertThat(model.getConstString()).isEqualTo("constString");
    }
}
