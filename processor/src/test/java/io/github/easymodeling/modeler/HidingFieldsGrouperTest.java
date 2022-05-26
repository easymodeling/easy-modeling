package io.github.easymodeling.modeler;

import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.number.ByteField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class HidingFieldsGrouperTest {

    @Test
    void should_not_mark_non_inherited_fields() {
        final Stream<ModelField> modelFields = Stream.of(fieldOf("foo"), fieldOf("bar"), fieldOf("baz"), fieldOf("qux"));

        final List<ModelField> markedFields = modelFields
                .collect(HidingFieldsGrouper.GROUPER)
                .values().stream().flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertThat(markedFields).hasSize(4);
        assertThat(markedFields).map(ModelField::fieldName).containsExactlyInAnyOrder("foo", "bar", "baz", "qux");
        markedFields.forEach(field -> {
            assertThat(field.isInherited()).isFalse();
            assertThat(field.isHidden()).isFalse();
        });
    }

    @Test
    void should_not_mark_inherited_fields_without_hidden_in_derived_type() {
        final Stream<ModelField> modelFields = Stream.of(
                fieldOf("foo"),
                inheritedFieldOf("bar"),
                inheritedFieldOf("baz"),
                fieldOf("qux"));

        final List<ModelField> markedFields = modelFields
                .collect(HidingFieldsGrouper.GROUPER)
                .values().stream().flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertThat(markedFields).hasSize(4)
                .map(ModelField::fieldName, ModelField::isInherited, ModelField::isHidden)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("foo", false, false),
                        Tuple.tuple("bar", true, false),
                        Tuple.tuple("baz", true, false),
                        Tuple.tuple("qux", false, false)
                );
    }

    @Test
    void should_mark_hidden_inherited_fields_as_hidden() {
        final Stream<ModelField> modelFields = Stream.of(
                fieldOf("foo"),
                inheritedFieldOf("bar"),
                fieldOf("baz"),
                inheritedFieldOf("baz"));

        final List<ModelField> markedFields = modelFields
                .collect(HidingFieldsGrouper.GROUPER)
                .values().stream().flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertThat(markedFields)
                .hasSize(4)
                .map(ModelField::fieldName, ModelField::isInherited, ModelField::isHidden)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("foo", false, false),
                        Tuple.tuple("bar", true, false),
                        Tuple.tuple("baz", false, false),
                        Tuple.tuple("baz", true, true)
                );
    }

    private ModelField inheritedFieldOf(String name) {
        final ModelField field = fieldOf(name);
        field.setInherited(true);
        return field;
    }

    private ModelField fieldOf(String name) {
        final FieldCustomization customization = FieldPatternFactory.one(name).build();
        return new ByteField().create(customization);
    }
}
