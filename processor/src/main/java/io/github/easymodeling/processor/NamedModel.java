package io.github.easymodeling.processor;

import io.github.easymodeling.Model;

public class NamedModel {

    private final String canonicalName;

    private final Model model;

    public NamedModel(String canonicalName) {
        this.canonicalName = canonicalName;
        this.model = null;
    }

    public NamedModel(String canonicalName, Model model) {
        this.canonicalName = canonicalName;
        this.model = model;
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
        return canonicalName.equals(((NamedModel) o).canonicalName);
    }
}
