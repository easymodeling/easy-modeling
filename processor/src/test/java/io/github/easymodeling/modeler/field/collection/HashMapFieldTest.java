package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.field.string.StringField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.collection.HashMapRandomizer;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class HashMapFieldTest extends ModelFieldTest {

    private StringField keyField;

    private IntegerField valueField;

    @Override
    @BeforeEach
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).min(3.).max(8.).maxSize(20).build();
        typeName = ParameterizedTypeName.get(HashMap.class, String.class, Integer.class);
        keyField = new StringField().create(fieldCustomization);
        valueField = new IntegerField().create(fieldCustomization);
        modelField = new HashMapField().create(fieldCustomization, keyField, valueField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(HashMapRandomizer.class) + "<>(" + keyField.initializer() + ", " + valueField.initializer() + ", 20)");
    }
}
