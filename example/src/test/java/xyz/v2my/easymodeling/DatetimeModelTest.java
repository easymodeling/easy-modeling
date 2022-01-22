package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatetimeModelTest {

    @Test
    void should_populate_datetime() {
        DatetimeModel model = DatetimeModelModeler.next();

        assertNotNull(model);
        assertNotNull(model.instant);
        assertNotNull(model.now);
        assertTrue(model.before.isBefore(Instant.parse("2000-01-01T00:00:00Z")));
        assertTrue(model.after.isAfter(Instant.parse("2000-01-01T00:00:00Z")));

        assertTrue(model.strings.length > 0);

        assertNotNull(model.localDate);
    }
}
