package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.TreeSetRandomizer;

import java.util.TreeSet;

public class TreeSetField extends AbstractCollectionField {

    public TreeSetField(FieldWrapper field, ModelField nestedField) {
        super(ClassName.get(TreeSet.class), field, nestedField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", TreeSetRandomizer.class);
    }
}
