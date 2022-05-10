package io.github.easymodeling.processor;

import io.github.easymodeling.Model;
import io.github.easymodeling.modeler.FieldCustomization;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnoModelWrapper {

    private final String canonicalName;

    private final Model model;

    public AnnoModelWrapper(String canonicalName) {
        this.canonicalName = canonicalName;
        this.model = null;
    }

    public AnnoModelWrapper(String canonicalName, Model model) {
        this.canonicalName = canonicalName;
        this.model = model;
    }

    public String getCanonicalName() {
        return canonicalName;
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

    public List<FieldCustomization> getFieldCustomizations() {
        return Optional.ofNullable(model).map(this::fieldsOf).orElse(Stream.empty())
                .map(AnnoFieldWrapper::toFieldCustomization)
                .collect(Collectors.toList());
    }

    private Stream<AnnoFieldWrapper> fieldsOf(Model model) {
        return Arrays.stream(model.fields()).map(field -> new AnnoFieldWrapper(this, field));
    }
}
