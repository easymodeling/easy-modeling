package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.HashSetRandomizer;

import java.util.HashSet;

public class HashSetField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(HashSet.class);

    public HashSetField() {
        this.type = TYPE;
    }

    @Override
    public HashSetField create(FieldWrapper field, ModelField... nestedFields) {
        return new HashSetField(field, nestedFields[0]);
    }

    private HashSetField(FieldWrapper field, ModelField nestedField) {
        super(TYPE, field, nestedField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", HashSetRandomizer.class);
    }
}
