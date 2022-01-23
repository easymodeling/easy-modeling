package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.FieldPattern;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.helper.FieldPatternFactory;
import xyz.v2my.easymodeling.randomizer.datetime.ZonedDateTimeRandomizer;

import java.time.Instant;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ZonedDateTimeFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).after("1991-01-23T00:00:00Z").before("1991-02-22T00:00:00Z").build();
        typeName = ClassName.get(ZonedDateTime.class);
        modelField = new ZonedDateTimeField().create(fieldPattern);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(ZonedDateTimeRandomizer.class) + "(664588800000L, 667180800000L)");
    }

    @Nested
    class ZonedDateTimeFieldConfigurationTest {

        @Test
        void should_create_initializer_as_now() {
            final FieldPattern fieldPattern = FieldPatternFactory.one(FIELD_NAME).now(true).build();
            modelField = new ZonedDateTimeField().create(fieldPattern);

            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(ZonedDateTimeRandomizer.class) + "(" + $(Instant.class) + ".now())");
        }

        @Test
        void should_create_initializer_as_constant() {
            final FieldPattern fieldPattern = FieldPatternFactory.one(FIELD_NAME).datetime("2000-01-01T00:00:00Z").build();
            modelField = new ZonedDateTimeField().create(fieldPattern);

            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(ZonedDateTimeRandomizer.class) + "(" + $(Instant.class) + ".ofEpochMilli(946684800000L))");
        }
    }
}
