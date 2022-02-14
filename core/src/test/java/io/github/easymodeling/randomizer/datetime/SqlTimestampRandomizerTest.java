package io.github.easymodeling.randomizer.datetime;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SqlTimestampRandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_sql_timestamp_within_range() {
        final Instant min = Instant.parse("2020-01-02T12:34:56Z");
        final Instant max = Instant.parse("2020-01-08T01:02:03Z");
        final SqlTimestampRandomizer sqlTimestampRandomizer = new SqlTimestampRandomizer(min.toEpochMilli(), max.toEpochMilli());

        final Timestamp next = sqlTimestampRandomizer.next();

        assertThat(next.toInstant()).isBetween(min, max);
    }

    @Test
    void should_generate_constant_sql_timestamp() {
        final Instant constant = Instant.parse("2020-01-02T12:34:56Z");
        final SqlTimestampRandomizer sqlTimestampRandomizer = new SqlTimestampRandomizer(constant);

        final Timestamp next = sqlTimestampRandomizer.next();

        assertThat(next.toInstant()).isEqualTo(constant);
    }
}
