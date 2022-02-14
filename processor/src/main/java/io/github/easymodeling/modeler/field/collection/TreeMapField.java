package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.collection.TreeMapRandomizer;

import java.util.TreeMap;

public class TreeMapField extends AbstractMapField {

    public static final ClassName TYPE = ClassName.get(TreeMap.class);

    public TreeMapField() {
        this.type = TYPE;
    }

    @Override
    public TreeMapField create(FieldPattern field, ModelField... nestedFields) {
        return new TreeMapField(field, nestedFields[0], nestedFields[1]);
    }

    private TreeMapField(FieldPattern field, ModelField keyField, ModelField valueField) {
        super(TYPE, field, keyField, valueField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", TreeMapRandomizer.class);
    }
}
