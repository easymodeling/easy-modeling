package xyz.v2my.easymodeling.factory.provider;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.OptionalField;
import xyz.v2my.easymodeling.factory.field.collection.ArrayListField;
import xyz.v2my.easymodeling.factory.field.collection.HashSetField;
import xyz.v2my.easymodeling.factory.field.collection.LinkedListField;
import xyz.v2my.easymodeling.factory.field.collection.ListField;
import xyz.v2my.easymodeling.factory.field.collection.SetField;
import xyz.v2my.easymodeling.factory.field.collection.TreeSetField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.v2my.easymodeling.factory.provider.PlainFieldProviderTest.plainFields;

public class ContainerFieldProviderTest extends ModelFieldProviderTest {

    @ParameterizedTest
    @MethodSource("containerFields")
    void should_provide_container_field_containing_plain_field(ClassName rawType, Class<SetField> containerField) {
        final FieldWrapper fieldWrapper = FieldWrapperFactory.any();

        plainFields().map(a -> a.get()[0])
                .map(TypeName.class::cast).forEach(typeName -> {
                    final ModelField container = modelFieldProvider.provide(ParameterizedTypeName.get(rawType, typeName), fieldWrapper);
                    assertThat(container).isInstanceOf(containerField);
                });
    }

    public static Stream<Arguments> containerFields() {
        return Stream.of(
                Arguments.of(ClassName.get(Optional.class), OptionalField.class),

                Arguments.of(ClassName.get(List.class), ListField.class),
                Arguments.of(ClassName.get(ArrayList.class), ArrayListField.class),
                Arguments.of(ClassName.get(LinkedList.class), LinkedListField.class),

                Arguments.of(ClassName.get(Set.class), SetField.class),
                Arguments.of(ClassName.get(HashSet.class), HashSetField.class),
                Arguments.of(ClassName.get(TreeSet.class), TreeSetField.class)
        );
    }
}
