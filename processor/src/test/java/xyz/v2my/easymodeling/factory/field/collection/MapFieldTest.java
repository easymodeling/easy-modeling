package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.field.string.StringField;
import xyz.v2my.easymodeling.factory.helper.FieldPatternFactory;
import xyz.v2my.easymodeling.randomizer.collection.MapRandomizer;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapFieldTest extends FieldTest {

    private StringField keyField;

    private IntegerField valueField;

    @Override
    @BeforeEach
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).min(3.).max(8.).minSize(10).maxSize(20).build();
        typeName = ParameterizedTypeName.get(Map.class, String.class, Integer.class);
        keyField = new StringField().create(fieldPattern);
        valueField = new IntegerField().create(fieldPattern);
        modelField = new MapField().create(fieldPattern, keyField, valueField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(MapRandomizer.class) + "<>(" + keyField.initializer() + ", " + valueField.initializer() + ", 10, 20)");
    }
}
