package io.github.easymodeling.processor;

import io.github.easymodeling.Field;
import io.github.easymodeling.modeler.FieldCustomization;

public class AnnoFieldWrapper {

    private final AnnoModelWrapper model;

    private final Field field;

    public AnnoFieldWrapper(AnnoModelWrapper model, Field field) {
        this.model = model;
        this.field = field;
    }

    public FieldCustomization toFieldCustomization() {
        return new FieldCustomization(
                model.getCanonicalName(),
                field.name(),
                field.max(),
                field.min(),
                field.constant(),
                field.alphanumeric(),
                field.alphabetic(),
                field.numeric(),
                field.string(),
                field.now(),
                field.before(),
                field.after(),
                field.future(),
                field.past(),
                field.datetime(),
                field.size(),
                field.minSize(),
                field.maxSize(),
                field.allowEmpty()
        );
    }
}
