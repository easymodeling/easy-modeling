package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.collection.TreeMapRandomizer;

import java.util.TreeMap;

public class TreeMapField extends AbstractMapField {

    public static final ClassName TYPE = ClassName.get(TreeMap.class);

    public TreeMapField() {
        this.type = TYPE;
    }

    @Override
    public TreeMapField create(FieldCustomization customization, ModelField... nestedFields) {
        return new TreeMapField(customization, nestedFields[0], nestedFields[1]);
    }

    private TreeMapField(FieldCustomization customization, ModelField keyField, ModelField valueField) {
        super(TYPE, customization, keyField, valueField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", TreeMapRandomizer.class);
    }
}
