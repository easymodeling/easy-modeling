package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.datetime.LocalTimeRandomizer;

import java.time.Instant;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class LocalTimeFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).after("1991-01-23T00:00:00Z").before("1991-02-22T00:00:00Z").build();
        typeName = ClassName.get(LocalTime.class);
        modelField = new LocalTimeField().create(fieldWrapper);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString("new " + $(LocalTimeRandomizer.class) + "(664588800000L, 667180800000L)");
    }

    @Nested
    class LocalTimeFieldConfigurationTest {

        @Test
        void should_create_initializer_as_now() {
            final FieldWrapper fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).now(true).build();
            modelField = new LocalTimeField().create(fieldWrapper);

            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(LocalTimeRandomizer.class) + "(" + $(Instant.class) + ".now())");
        }

        @Test
        void should_create_initializer_as_constant() {
            final FieldWrapper fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).datetime("2000-01-01T00:00:00Z").build();
            modelField = new LocalTimeField().create(fieldWrapper);

            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(LocalTimeRandomizer.class) + "(" + $(Instant.class) + ".ofEpochMilli(946684800000L))");
        }
    }
}
