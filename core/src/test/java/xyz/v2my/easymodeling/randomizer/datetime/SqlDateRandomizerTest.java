package xyz.v2my.easymodeling.randomizer.datetime;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.randomizer.RandomizerTest;

import java.sql.Date;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SqlDateRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_sql_date_within_range() {
        final Instant min = Instant.parse("2020-01-02T00:00:00Z");
        final Instant max = Instant.parse("2020-01-08T00:00:00Z");
        final SqlDateRandomizer sqlDateRandomizer = new SqlDateRandomizer(min.toEpochMilli(), max.toEpochMilli());

        final Date next = sqlDateRandomizer.next();

        assertThat(next).isBetween(Date.valueOf("2020-01-02"), Date.valueOf("2020-01-08"), true, true);
    }

    @Test
    void should_generate_constant_sql_date() {
        final Instant constant = Instant.parse("2020-01-02T00:00:00Z");
        final SqlDateRandomizer sqlDateRandomizer = new SqlDateRandomizer(constant);

        final Date next = sqlDateRandomizer.next();

        assertThat(next).isEqualTo(Date.valueOf("2020-01-02"));
    }
}
