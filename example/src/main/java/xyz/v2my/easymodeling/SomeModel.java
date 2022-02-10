package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class SomeModel {

    public String string;

    public Instant instant;

    public SomeNestedModel someNestedModel;
}
