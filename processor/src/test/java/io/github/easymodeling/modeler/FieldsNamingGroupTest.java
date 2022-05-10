package io.github.easymodeling.modeler;

import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.number.ByteField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FieldsNamingGroupTest {

    @Test
    void should_create_list_of_fields_groups() {
        final List<ModelField> modelFields = Arrays.asList(fieldOf("foo"), inheritedFieldOf("bar"), fieldOf("baz"), inheritedFieldOf("baz"));
        final Stream<FieldsNamingGroup> groups = FieldsNamingGroup.grouping(modelFields);

        final List<FieldsNamingGroup> fieldsNamingGroups = groups.collect(Collectors.toList());
        assertThat(fieldsNamingGroups).hasSize(3);
        assertThat(fieldsNamingGroups.stream().flatMap(FieldsNamingGroup::getFields))
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
        final FieldCustomization customization = FieldPatternFactory.one(name).build();
        final ModelField field = new ByteField().create(customization);
        field.setInherited(true);
        return field;
    }

    private ModelField fieldOf(String name) {
        final FieldCustomization customization = FieldPatternFactory.one(name).build();
        return new ByteField().create(customization);
    }
}
