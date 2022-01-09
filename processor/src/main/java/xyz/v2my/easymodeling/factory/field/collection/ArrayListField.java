package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.ArrayListRandomizer;

import java.util.List;

public class ArrayListField extends ListField {

    public ArrayListField() {
    }

    private ArrayListField(TypeName type, FieldWrapper field, List<ModelField> nestedFields) {
        super(type, field, nestedFields);
    }

    @Override
    public ArrayListField create(TypeName type, FieldWrapper field, List<ModelField> nestedFields) {
        return new ArrayListField(type, field, nestedFields);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ArrayListRandomizer.class);
    }
}
