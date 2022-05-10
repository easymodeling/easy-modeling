package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.datetime.LocalDateRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateFieldTest extends ModelFieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).after("1991-01-23T00:00:00Z").before("1991-02-22T00:00:00Z").build();
        typeName = ClassName.get(LocalDate.class);
        modelField = new LocalDateField().create(fieldCustomization);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString("new " + $(LocalDateRandomizer.class) + "(664588800000L, 667180800000L)");
    }

    @Nested
    class LocalDateFieldConfigurationTest {

        @Test
        void should_create_initializer_as_now() {
            final FieldCustomization fieldCustomization = FieldPatternFactory.one(FIELD_NAME).now(true).build();
            modelField = new LocalDateField().create(fieldCustomization);

            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(LocalDateRandomizer.class) + "(" + $(Instant.class) + ".now())");
        }

        @Test
        void should_create_initializer_as_constant() {
            final FieldCustomization fieldCustomization = FieldPatternFactory.one(FIELD_NAME).datetime("2000-01-01T00:00:00Z").build();
            modelField = new LocalDateField().create(fieldCustomization);

            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(LocalDateRandomizer.class) + "(" + $(Instant.class) + ".ofEpochMilli(946684800000L))");
        }
    }
}
