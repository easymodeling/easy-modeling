package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.collection.MapRandomizer;

import java.util.Map;

public class MapField extends AbstractMapField {

    public static final ClassName TYPE = ClassName.get(Map.class);

    public MapField() {
        this.type = TYPE;
    }

    @Override
    public MapField create(FieldCustomization customization, ModelField... nestedFields) {
        return new MapField(customization, nestedFields[0], nestedFields[1]);
    }

    private MapField(FieldCustomization customization, ModelField keyField, ModelField valueField) {
        super(TYPE, customization, keyField, valueField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", MapRandomizer.class);
    }
}
