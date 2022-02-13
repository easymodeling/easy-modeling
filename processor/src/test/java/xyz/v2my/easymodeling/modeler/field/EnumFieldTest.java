package xyz.v2my.easymodeling.modeler.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.modeler.helper.FieldPatternFactory;
import xyz.v2my.easymodeling.randomizer.EnumRandomizer;

import static org.assertj.core.api.Assertions.assertThat;

class EnumFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).build();
        typeName = ClassName.get(SomeEnum.class);
        modelField = new EnumField(typeName, fieldPattern);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString("new " + $(EnumRandomizer.class) + "<>(" + $(SomeEnum.class) + ".values())");
    }

    enum SomeEnum {
        A, B, C
    }
}
