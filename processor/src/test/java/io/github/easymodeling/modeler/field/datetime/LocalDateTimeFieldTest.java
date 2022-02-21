package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.datetime.LocalDateTimeRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateTimeFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).after("1991-01-23T00:00:00Z").before("1991-02-22T00:00:00Z").build();
        typeName = ClassName.get(LocalDateTime.class);
        modelField = new LocalDateTimeField().create(fieldPattern);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString("new " + $(LocalDateTimeRandomizer.class) + "(664588800000L, 667180800000L)");
    }

    @Nested
    class LocalDateTimeFieldConfigurationTest {

        @Test
        void should_create_initializer_as_now() {
            final FieldPattern fieldPattern = FieldPatternFactory.one(FIELD_NAME).now(true).build();
            modelField = new LocalDateTimeField().create(fieldPattern);

            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(LocalDateTimeRandomizer.class) + "(" + $(Instant.class) + ".now())");
        }

        @Test
        void should_create_initializer_as_constant() {
            final FieldPattern fieldPattern = FieldPatternFactory.one(FIELD_NAME).datetime("2000-01-01T00:00:00Z").build();
            modelField = new LocalDateTimeField().create(fieldPattern);

            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(LocalDateTimeRandomizer.class) + "(" + $(Instant.class) + ".ofEpochMilli(946684800000L))");
        }
    }
}
