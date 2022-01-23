package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldPattern;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.TreeSetRandomizer;

import java.util.TreeSet;

public class TreeSetField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(TreeSet.class);

    public TreeSetField() {
        this.type = TYPE;
    }

    @Override
    public TreeSetField create(FieldPattern field, ModelField... nestedFields) {
        return new TreeSetField(field, nestedFields[0]);
    }

    private TreeSetField(FieldPattern field, ModelField nestedField) {
        super(TYPE, field, nestedField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", TreeSetRandomizer.class);
    }
}
