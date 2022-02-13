package xyz.v2my.easymodeling.modeler.provider;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.modeler.field.OptionalField;
import xyz.v2my.easymodeling.modeler.field.collection.ArrayListField;
import xyz.v2my.easymodeling.modeler.field.collection.HashSetField;
import xyz.v2my.easymodeling.modeler.field.collection.LinkedListField;
import xyz.v2my.easymodeling.modeler.field.collection.ListField;
import xyz.v2my.easymodeling.modeler.field.collection.SetField;
import xyz.v2my.easymodeling.modeler.field.collection.TreeSetField;
import xyz.v2my.easymodeling.modeler.field.stream.StreamField;
import xyz.v2my.easymodeling.modeler.helper.FieldPatternFactory;
import xyz.v2my.easymodeling.modeler.provider.mock.Factory;

import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.v2my.easymodeling.modeler.provider.PlainFieldProviderTest.plainFields;

public class ContainerFieldProviderTest extends ModelFieldProviderTest {

    @ParameterizedTest
    @MethodSource("containerFields")
    void should_provide_container_field_containing_plain_field(Class<?> clazz, Class<SetField> containerField) {
        final FieldPattern fieldPattern = FieldPatternFactory.any();

        plainFields().map(a -> a.get()[0])
                .map(TypeMirror.class::cast)
                .forEach(subType -> {
                    final ModelField container = modelFieldProvider.provide(Factory.container(clazz, subType), fieldPattern);
                    assertThat(container).isInstanceOf(containerField);
                });
    }

    public static Stream<Arguments> containerFields() {
        return Stream.of(
                Arguments.of(Optional.class, OptionalField.class),

                Arguments.of(List.class, ListField.class),
                Arguments.of(ArrayList.class, ArrayListField.class),
                Arguments.of(LinkedList.class, LinkedListField.class),

                Arguments.of(Set.class, SetField.class),
                Arguments.of(HashSet.class, HashSetField.class),
                Arguments.of(TreeSet.class, TreeSetField.class),

                Arguments.of(Stream.class, StreamField.class)
        );
    }
}
