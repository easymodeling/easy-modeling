package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatetimeModelTest {

    @Test
    void should_populate_datetime() {
        DatetimeModel model = EMDatetimeModel.next();

        assertNotNull(model);
        assertNotNull(model.getInstant());
    }
}
