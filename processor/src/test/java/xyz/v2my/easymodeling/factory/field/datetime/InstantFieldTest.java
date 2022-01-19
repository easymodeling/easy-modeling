package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.datetime.InstantRandomizer;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class InstantFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).build();
        typeName = ClassName.get(Instant.class);
        modelField = new InstantField().create(fieldWrapper);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).isEqualTo(CodeBlock.of("new " + $(InstantRandomizer.class) + "(0L, 2147483647000L)"));
    }

    @Nested
    class InstantFieldConfigurationTest {

        @Test
        void should_create_initializer_as_now() {
            final FieldWrapper fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).now(true).build();
            modelField = new InstantField().create(fieldWrapper);

            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).isEqualTo(CodeBlock.of("new " + $(InstantRandomizer.class) + "(" + $(Instant.class) + ".now())"));
        }
    }

    // TODO: 16.01.22 more tests for field configurations
}
