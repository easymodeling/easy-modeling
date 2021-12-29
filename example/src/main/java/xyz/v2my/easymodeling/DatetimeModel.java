package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class DatetimeModel {

    private Instant instant;

    public Instant getInstant() {
        return instant;
    }
}
