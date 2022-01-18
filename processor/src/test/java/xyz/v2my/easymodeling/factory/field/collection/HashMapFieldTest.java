package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.field.string.StringField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.collection.HashMapRandomizer;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class HashMapFieldTest extends FieldTest {

    private StringField keyField;

    private IntegerField valueField;

    @Override
    @BeforeEach
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).min(3.).max(8.).minSize(10).maxSize(20).build();
        typeName = ParameterizedTypeName.get(HashMap.class, String.class, Integer.class);
        keyField = new StringField(fieldWrapper);
        valueField = new IntegerField(fieldWrapper);
        modelField = new HashMapField(fieldWrapper, keyField, valueField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer.toString())
                .isEqualTo("new " + $(HashMapRandomizer.class) + "<>(" + keyField.initializer() + ", " + valueField.initializer() + ", 10, 20)");
    }
}
