package io.github.easymodeling.processor;

import io.github.easymodeling.Model;
import io.github.easymodeling.modeler.FieldCustomization;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AnnoModelWrapper {

    private final String canonicalName;

    private final Model model;

    private final List<AnnoFieldWrapper> fields;

    public AnnoModelWrapper(String canonicalName) {
        this.canonicalName = canonicalName;
        this.model = null;
        this.fields = Collections.emptyList();
    }

    public AnnoModelWrapper(String canonicalName, Model model) {
        this.canonicalName = canonicalName;
        this.model = model;
        this.fields = fieldsOf(model);
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public int hashCode() {
        return canonicalName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        return canonicalName.equals(((AnnoModelWrapper) o).canonicalName);
    }

    private List<AnnoFieldWrapper> fieldsOf(Model model) {
        return Arrays.stream(model.fields())
                .map(field -> new AnnoFieldWrapper(this, field))
                .collect(Collectors.toList());
    }

    public List<FieldCustomization> getFieldCustomizations() {
        return fields.stream().map(AnnoFieldWrapper::toFieldCustomization).collect(Collectors.toList());
    }
}
