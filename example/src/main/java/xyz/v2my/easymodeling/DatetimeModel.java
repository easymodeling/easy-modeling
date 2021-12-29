package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class DatetimeModel {

    private Instant instant;

    private Instant now;

    private Instant before;

    private Instant after;

    public Instant getInstant() {
        return instant;
    }

    public Instant getNow() {
        return now;
    }

    public Instant getBefore() {
        return before;
    }

    public Instant getAfter() {
        return after;
    }
}
