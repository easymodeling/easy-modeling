package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.collection.HashSetRandomizer;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class HashSetFieldTest extends FieldTest {

    private IntegerField integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).minSize(50).maxSize(100).build();
        integerField = new IntegerField(fieldWrapper);
        typeName = ParameterizedTypeName.get(HashSet.class, Integer.class);
        modelField = new HashSetField(fieldWrapper, Collections.singletonList(integerField));
    }

    @Override
    @Test
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer.toString()).isEqualTo(
                "new " + $(HashSetRandomizer.class) + "<>(" + integerField.initializer() + ", 50, 100)");
    }
}
