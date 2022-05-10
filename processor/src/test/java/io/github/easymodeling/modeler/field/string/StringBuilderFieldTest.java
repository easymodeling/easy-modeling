package io.github.easymodeling.modeler.field.string;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.string.StringBuilderRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.easymodeling.randomizer.string.CharSequenceRandomizer.ALPHABETIC;
import static org.assertj.core.api.Assertions.assertThat;

class StringBuilderFieldTest extends ModelFieldTest {

    public static final String STRING_CONSTANT = "some-string";

    @BeforeEach
    @Override
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).string(STRING_CONSTANT).build();
        typeName = ClassName.get(StringBuilder.class);
        modelField = new StringBuilderField().create(fieldCustomization);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(StringBuilderRandomizer.class) + "(\"" + STRING_CONSTANT + "\")");
    }

    @Test
    void should_generate_initializer_with_min_max_and_char_range() {
        FieldCustomization fieldCustomization = FieldPatternFactory.one(FIELD_NAME).min(2.).max(40.).alphabetic(true).build();
        ModelField modelField = new StringBuilderField().create(fieldCustomization);
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(StringBuilderRandomizer.class) + "(2, 40, " + ALPHABETIC + ")");
    }
}
