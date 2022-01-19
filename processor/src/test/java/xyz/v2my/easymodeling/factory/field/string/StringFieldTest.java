package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.string.StringRandomizer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static xyz.v2my.easymodeling.randomizer.string.CharSequenceRandomizer.NUMERIC;

class StringFieldTest extends FieldTest {

    public static final String STRING_CONSTANT = "some-string";

    @BeforeEach
    @Override
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).string(STRING_CONSTANT).build();
        typeName = ClassName.get(String.class);
        modelField = new StringField().create(fieldWrapper);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer.toString())
                .isEqualTo("new " + $(StringRandomizer.class) + "(\"" + STRING_CONSTANT + "\")");
    }

    @Nested
    class ConfigurationTest {

        @Test
        protected void should_generate_initializer_with_min_max_and_charset() {
            FieldWrapper fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).min(2.).max(40.).numeric(true).build();
            ModelField modelField = new StringField().create(fieldWrapper);
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer.toString())
                    .isEqualTo("new " + $(StringRandomizer.class) + "(2, 40, " + NUMERIC + ")");
        }

        @Test
        void should_throw_when_charset_now_set_well() {
            FieldWrapper fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).numeric(true).alphabetic(true).build();
            ModelField modelField = new StringField().create(fieldWrapper);

            //noinspection Convert2MethodRef
            final Throwable throwable = catchThrowable(() -> modelField.initializer());

            assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
