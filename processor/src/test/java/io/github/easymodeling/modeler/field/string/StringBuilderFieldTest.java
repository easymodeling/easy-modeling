package io.github.easymodeling.modeler.field.string;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.string.StringBuilderRandomizer;

import static org.assertj.core.api.Assertions.assertThat;
import static io.github.easymodeling.randomizer.string.CharSequenceRandomizer.ALPHABETIC;

class StringBuilderFieldTest extends FieldTest {

    public static final String STRING_CONSTANT = "some-string";

    @BeforeEach
    @Override
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).string(STRING_CONSTANT).build();
        typeName = ClassName.get(StringBuilder.class);
        modelField = new StringBuilderField().create(fieldPattern);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(StringBuilderRandomizer.class) + "(\"" + STRING_CONSTANT + "\")");
    }

    @Test
    void should_generate_initializer_with_min_max_and_charset() {
        FieldPattern fieldPattern = FieldPatternFactory.one(FIELD_NAME).min(2.).max(40.).alphabetic(true).build();
        ModelField modelField = new StringBuilderField().create(fieldPattern);
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(StringBuilderRandomizer.class) + "(2, 40, " + ALPHABETIC + ")");
    }
}
