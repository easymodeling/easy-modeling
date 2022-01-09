package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayListFieldTest extends FieldTest {

    @BeforeEach
    void setUp() {
        final FieldWrapper fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).build();
        final PlainField<Integer> integerField = new IntegerField(TypeName.get(Integer.class), fieldWrapper);
        typeName = ParameterizedTypeName.get(ArrayList.class, Integer.class);
        modelField = new ArrayListField(typeName, fieldWrapper, Collections.singletonList(integerField));
    }

    @Override
    @Test
    protected void should_generate_builder_setter() {
        final FieldWrapper fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).build();
        final PlainField<Integer> integerField = new IntegerField(TypeName.get(Integer.class), fieldWrapper);
        final ArrayListField listField = new ArrayListField(ParameterizedTypeName.get(ArrayList.class, Integer.class), fieldWrapper, Collections.singletonList(integerField));

        final MethodSpec builder = listField.setter("Builder");

        assertThat(builder.name).isEqualTo("fieldName");
    }
}
