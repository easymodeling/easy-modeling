package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldPattern;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.ArrayListRandomizer;

import java.util.ArrayList;

public class ArrayListField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(ArrayList.class);

    public ArrayListField() {
        this.type = TYPE;
    }

    @Override
    public ArrayListField create(FieldPattern field, ModelField... nestedFields) {
        return new ArrayListField(field, nestedFields[0]);
    }

    private ArrayListField(FieldPattern field, ModelField nestedField) {
        super(TYPE, field, nestedField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ArrayListRandomizer.class);
    }
}
