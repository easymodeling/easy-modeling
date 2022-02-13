package xyz.v2my.easymodeling.modeler.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.modeler.field.FieldTest;
import xyz.v2my.easymodeling.modeler.field.number.IntegerField;
import xyz.v2my.easymodeling.modeler.field.string.StringField;
import xyz.v2my.easymodeling.modeler.helper.FieldPatternFactory;
import xyz.v2my.easymodeling.randomizer.collection.TreeMapRandomizer;

import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

class TreeMapFieldTest extends FieldTest {

    private StringField keyField;

    private IntegerField valueField;

    @Override
    @BeforeEach
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).min(3.).max(8.).minSize(10).maxSize(20).build();
        typeName = ParameterizedTypeName.get(TreeMap.class, String.class, Integer.class);
        keyField = new StringField().create(fieldPattern);
        valueField = new IntegerField().create(fieldPattern);
        modelField = new TreeMapField().create(fieldPattern, keyField, valueField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(TreeMapRandomizer.class) + "<>(" + keyField.initializer() + ", " + valueField.initializer() + ", 10, 20)");
    }
}