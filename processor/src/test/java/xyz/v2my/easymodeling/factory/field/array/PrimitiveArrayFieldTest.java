package xyz.v2my.easymodeling.factory.field.array;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.array.PrimitiveArrayRandomizer;

import javax.lang.model.element.Modifier;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PrimitiveArrayFieldTest extends FieldTest {

    public static final String FIELD_NAME = "int_field";
    private PrimitiveArrayField primitiveArrayField;

    private PlainField<Integer> integerField;

    @BeforeEach
    void setUp() {
        final FieldWrapper field = FieldWrapperFactory.one(FIELD_NAME).minLength(3).maxLength(8).build();
        integerField = new IntegerField().create(ClassName.get(Integer.class), field);
        primitiveArrayField = new PrimitiveArrayField(ArrayTypeName.of(int[].class), field, integerField);
    }

    @Test
    void should_generate_initial_value() {
        final CodeBlock initialValue = primitiveArrayField.initialValue();

        assertThat(initialValue.toString()).isEqualTo(
                "(int[][]) new " + $(PrimitiveArrayRandomizer.class) + "(" + integerField.initializer().toString() + ", 2, 3, 8).next()");
    }

    @Test
    @Override
    protected void should_generate_builder_field() {
        final FieldSpec field = primitiveArrayField.field();

        assertThat(field.name).contains(FIELD_NAME);
        assertThat(field.type).isEqualTo(ArrayTypeName.of(int[].class));
        assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
    }

    @Test
    @Override
    protected void should_generate_builder_setter() {
        final MethodSpec setter = primitiveArrayField.setter("Builder");

        assertThat(setter.name).isEqualTo(FIELD_NAME);
        assertThat(setter.returnType.toString()).isEqualTo("Builder");
        assertThat(setter.modifiers).containsExactly(Modifier.PUBLIC);
    }

    @Test
    void should_not_create_field() {
        final FieldWrapper field = FieldWrapperFactory.one(FIELD_NAME).minLength(3).maxLength(8).build();
        final List<ModelField> integerField = Collections.singletonList(new IntegerField());

        final Throwable throwable = catchThrowable(() -> primitiveArrayField.create(ArrayTypeName.of(int[].class), field, integerField));

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }
}
