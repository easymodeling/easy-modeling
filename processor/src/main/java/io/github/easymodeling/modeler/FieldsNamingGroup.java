package io.github.easymodeling.modeler;

import io.github.easymodeling.modeler.field.ModelField;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FieldsNamingGroup {

    private final List<ModelField> fields;

    public FieldsNamingGroup(List<ModelField> fields) {
        this.fields = fields;
        this.markHiddenFields();
    }

    public Stream<ModelField> getFields() {
        return fields.stream();
    }

    public static Stream<FieldsNamingGroup> grouping(List<ModelField> modelFields) {
        return modelFields.stream()
                .collect(Collectors.groupingBy(ModelField::fieldName))
                .values().stream()
                .map(FieldsNamingGroup::new);
    }

    private void markHiddenFields() {
        if (notContainsHiddenFields()) {
            return;
        }
        fields.stream().filter(ModelField::isInherited).forEach(ModelField::setHidden);
    }

    private boolean notContainsHiddenFields() {
        return fields.size() <= 1;
    }
}
