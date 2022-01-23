package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldPatternFactory;
import xyz.v2my.easymodeling.randomizer.collection.TreeSetRandomizer;

import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

class TreeSetFieldTest extends FieldTest {

    private IntegerField integerField;

    @Override
    @BeforeEach
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).minSize(50).maxSize(100).build();
        integerField = new IntegerField().create(fieldPattern);
        typeName = ParameterizedTypeName.get(TreeSet.class, Integer.class);
        modelField = new TreeSetField().create(fieldPattern, integerField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString(
                "new " + $(TreeSetRandomizer.class) + "<>(" + integerField.initializer() + ", 50, 100)");
    }
}
