package xyz.v2my.easymodeling.factory.field;

import org.junit.jupiter.api.Test;

public abstract class FieldTest {

    protected String $(Class<?> clazz) {
        return clazz.getCanonicalName();
    }

    @Test
    abstract protected void should_generate_builder_field();

    @Test
    abstract protected void should_generate_builder_setter();
}
