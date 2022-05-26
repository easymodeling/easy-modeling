package io.github.easymodeling;

import org.assertj.core.data.TemporalUnitWithinOffset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DatetimeModelTest {

    @Test
    @Timeout(3)
    void should_populate_datetime() {
        DatetimeModel model = DatetimeModelModeler.next();

        assertThat(model).isNotNull();

        assertThat(model.instant).isNotNull();
        assertThat(model.now).isNotNull();
        assertThat(model.before).isBefore(Instant.parse("2000-01-01T00:00:00Z"));
        assertThat(model.after).isAfter(Instant.parse("2000-01-01T00:00:00Z"));

        assertThat(model.strings).isNotEmpty();

        assertThat(model.localDate).isNotNull();
        assertThat(model.localTime).isNotNull();
        assertThat(model.localDateTime).isNotNull();

        assertThat(model.date).isNotNull();
        assertThat(model.nowDate).isNotNull();
        assertThat(model.constantDate).isNotNull();

        assertThat(model.nowDate.toInstant()).isCloseTo(Instant.now(), new TemporalUnitWithinOffset(3, ChronoUnit.SECONDS));
        assertThat(model.constantDate.toInstant()).isEqualTo(Instant.parse("2000-01-01T00:00:00Z"));
    }

    @Test
    void should_create_a_list_of_datetime() {
        final List<DatetimeModel> models = DatetimeModelModeler.list();

        assertThat(models).hasSizeBetween(1, 7);
        models.forEach(model -> {
            assertThat(model.instant).isNotNull();
            assertThat(model.now).isNotNull();
        });
    }
}
