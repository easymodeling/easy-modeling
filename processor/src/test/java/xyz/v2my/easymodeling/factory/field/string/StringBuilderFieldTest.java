package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.string.StringBuilderRandomizer;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.v2my.easymodeling.randomizer.string.CharSequenceRandomizer.ALPHABETIC;

class StringBuilderFieldTest extends FieldTest {

    public static final String STRING_CONSTANT = "some-string";

    @BeforeEach
    void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).string(STRING_CONSTANT).build();
        typeName = ClassName.get(StringBuilder.class);
        modelField = new StringBuilderField(fieldWrapper);
    }

    @Override
    @Test
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer.toString())
                .isEqualTo("new " + $(StringBuilderRandomizer.class) + "(\"" + STRING_CONSTANT + "\")");
    }

    @Test
    protected void should_generate_initializer_with_min_max_and_charset() {
        FieldWrapper fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).min(2.).max(40.).alphabetic(true).build();
        ModelField modelField = new StringBuilderField(fieldWrapper);
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer.toString())
                .isEqualTo("new " + $(StringBuilderRandomizer.class) + "(2, 40, " + ALPHABETIC + ")");
    }
}
