package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringTypeModelTest {

    private final StringTypeModel model = EMStringTypeModel.next();

    @Test
    void getCommonString() {
        final int length = model.getCommonString().length();
        assertTrue(length >= 10 && length < 20);
    }

    @Test
    void getAlphanumericString() {
        Pattern alphanumericPattern = Pattern.compile("^[a-zA-Z0-9]+$");

        assertTrue(alphanumericPattern.matcher(model.getAlphanumericString()).matches());
    }

    @Test
    void getAlphabeticString() {
        Pattern alphabeticPattern = Pattern.compile("^[a-zA-Z]+$");

        assertTrue(alphabeticPattern.matcher(model.getAlphabeticString()).matches());
    }

    @Test
    void getNumericString() {
        Pattern numericPattern = Pattern.compile("^[0-9]+$");

        assertTrue(numericPattern.matcher(model.getNumericString()).matches());
    }

    @Test
    void getConstString() {
        assertEquals("constString", model.getConstString());
    }
}
