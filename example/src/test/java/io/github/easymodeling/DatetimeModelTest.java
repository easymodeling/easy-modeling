package io.github.easymodeling;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatetimeModelTest {

    @Test
    @Timeout(3)
    void should_populate_datetime() {
        DatetimeModel model = DatetimeModelModeler.next();

        assertNotNull(model);
        assertNotNull(model.instant);
        assertNotNull(model.now);
        assertTrue(model.before.isBefore(Instant.parse("2000-01-01T00:00:00Z")));
        assertTrue(model.after.isAfter(Instant.parse("2000-01-01T00:00:00Z")));

        assertTrue(model.strings.length > 0);

        assertNotNull(model.localDate);
        assertNotNull(model.localTime);
        assertNotNull(model.localDateTime);

        assertNotNull(model.date);
        assertNotNull(model.nowDate);
        assertNotNull(model.constantDate);

        assertEquals(model.nowDate.toInstant().getEpochSecond(), Instant.now().getEpochSecond(), 3);
        assertEquals(model.constantDate.toInstant(), Instant.parse("2000-01-01T00:00:00Z"));
    }
}
