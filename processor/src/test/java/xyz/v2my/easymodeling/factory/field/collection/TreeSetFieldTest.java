package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.collection.TreeSetRandomizer;

import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

class TreeSetFieldTest extends FieldTest {

    private IntegerField integerField;

    @Override
    @BeforeEach
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).minSize(50).maxSize(100).build();
        integerField = new IntegerField(fieldWrapper);
        typeName = ParameterizedTypeName.get(TreeSet.class, Integer.class);
        modelField = new TreeSetField(fieldWrapper, integerField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer.toString()).isEqualTo(
                "new " + $(TreeSetRandomizer.class) + "<>(" + integerField.initializer() + ", 50, 100)");
    }
}
