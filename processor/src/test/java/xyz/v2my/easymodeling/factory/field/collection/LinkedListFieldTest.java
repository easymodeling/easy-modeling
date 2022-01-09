package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.collection.LinkedListRandomizer;

import java.util.Collections;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

class LinkedListFieldTest extends FieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).minSize(2).maxSize(5).build();
        typeName = ParameterizedTypeName.get(LinkedList.class, Integer.class);
        integerField = new IntegerField(fieldWrapper);
        modelField = new LinkedListField(ClassName.get(Integer.class), fieldWrapper, Collections.singletonList(integerField));
    }

    @Test
    @Override
    protected void should_generate_initializer() {
        final CodeBlock codeBlock = modelField.initializer();

        assertThat(codeBlock.toString())
                .isEqualTo("new " + $(LinkedListRandomizer.class) + "<>(" + integerField.initializer() + ", 2, 5)");
    }
}
