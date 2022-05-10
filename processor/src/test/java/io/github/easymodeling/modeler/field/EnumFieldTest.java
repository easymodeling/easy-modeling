package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.EnumRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class EnumFieldTest extends ModelFieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).build();
        typeName = ClassName.get(SomeEnum.class);
        modelField = new EnumField(typeName, fieldCustomization);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString("new " + $(EnumRandomizer.class) + "<>(" + $(SomeEnum.class) + ".values())");
    }

    @Test
    void should_not_create_enum_fields() {
        final Throwable throwable = catchThrowable(() -> modelField.create(fieldCustomization));

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }

    enum SomeEnum {
        A, B, C
    }
}
