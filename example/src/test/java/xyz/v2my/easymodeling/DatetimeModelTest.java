package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatetimeModelTest {

    @Test
    void should_populate_datetime() {
        DatetimeModel model = EMDatetimeModel.next();

        assertNotNull(model);
        assertNotNull(model.getInstant());
        assertNotNull(model.getNow());
        assertTrue(model.getBefore().isBefore(Instant.parse("2000-01-01T00:00:00Z")));
        assertTrue(model.getAfter().isAfter(Instant.parse("2000-01-01T00:00:00Z")));

        assertTrue(model.getStrings().length > 0);
    }
}
