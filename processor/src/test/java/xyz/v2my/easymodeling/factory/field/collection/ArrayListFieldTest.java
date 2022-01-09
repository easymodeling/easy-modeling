package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayListFieldTest extends FieldTest {

    @Override
    @Test
    protected void should_generate_builder_field() {
        final FieldWrapper fieldWrapper = FieldWrapperFactory.one("fieldName").build();
        final PlainField<Integer> integerField = new IntegerField().create(TypeName.get(Integer.class), fieldWrapper);
        final ArrayListField listField = new ArrayListField().create(ParameterizedTypeName.get(ArrayList.class, Integer.class), fieldWrapper, Collections.singletonList(integerField));

        final FieldSpec field = listField.field();

        assertThat(field.name).contains("fieldName");
        assertThat(field.type).isEqualTo(ParameterizedTypeName.get(ArrayList.class, Integer.class));
        assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
    }

    @Override
    @Test
    protected void should_generate_builder_setter() {
        final FieldWrapper fieldWrapper = FieldWrapperFactory.one("fieldName").build();
        final PlainField<Integer> integerField = new IntegerField().create(TypeName.get(Integer.class), fieldWrapper);
        final ArrayListField listField = new ArrayListField().create(ParameterizedTypeName.get(ArrayList.class, Integer.class), fieldWrapper, Collections.singletonList(integerField));

        final MethodSpec builder = listField.setter("Builder");

        assertThat(builder.name).contains("fieldName");
    }
}
