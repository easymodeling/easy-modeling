package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.datetime.InstantRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class InstantFieldTest extends ModelFieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).build();
        typeName = ClassName.get(Instant.class);
        modelField = new InstantField().create(fieldCustomization);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString("new " + $(InstantRandomizer.class) + "(0L, 2147483647000L)");
    }

    @Nested
    class InstantFieldConfigurationTest {

        @Test
        void should_create_initializer_as_now() {
            final FieldCustomization fieldCustomization = FieldPatternFactory.one(FIELD_NAME).now(true).build();
            modelField = new InstantField().create(fieldCustomization);

            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(InstantRandomizer.class) + "(" + $(Instant.class) + ".now())");
        }

        @Test
        void should_create_initializer_as_constant() {
            final FieldCustomization fieldCustomization = FieldPatternFactory.one(FIELD_NAME).datetime("2000-01-01T00:00:00Z").build();
            modelField = new InstantField().create(fieldCustomization);

            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(InstantRandomizer.class) + "(" + $(Instant.class) + ".ofEpochMilli(946684800000L))");
        }
    }
}
