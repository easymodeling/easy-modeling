package io.github.easymodeling.modeler.field.string;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.string.StringRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.github.easymodeling.randomizer.string.CharSequenceRandomizer.NUMERIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class StringFieldTest extends FieldTest {

    public static final String STRING_CONSTANT = "some-string";

    @BeforeEach
    @Override
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).string(STRING_CONSTANT).build();
        typeName = ClassName.get(String.class);
        modelField = new StringField().create(fieldPattern);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(StringRandomizer.class) + "(\"" + STRING_CONSTANT + "\")");
    }

    @Nested
    class ConfigurationTest {

        @Test
        void should_generate_initializer_with_min_max_and_char_range() {
            FieldPattern fieldPattern = FieldPatternFactory.one(FIELD_NAME).min(2.).max(40.).numeric(true).build();
            ModelField modelField = new StringField().create(fieldPattern);
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer)
                    .hasToString("new " + $(StringRandomizer.class) + "(2, 40, " + NUMERIC + ")");
        }

        @Test
        void should_throw_when_char_range_now_set_well() {
            FieldPattern fieldPattern = FieldPatternFactory.one(FIELD_NAME).numeric(true).alphabetic(true).build();
            ModelField modelField = new StringField().create(fieldPattern);

            //noinspection Convert2MethodRef
            final Throwable throwable = catchThrowable(() -> modelField.initializer());

            assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
