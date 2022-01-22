package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@AllArgsConstructor
public class DatetimeModel {

    public Instant instant;

    public Instant now;

    public Instant before;

    public Instant after;

    public String[] strings;

    public LocalDate localDate;
}
