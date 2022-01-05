package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.ListRandomizer;

import java.util.List;

public class ListField extends Container {

    public ListField() {
    }

    private ListField(TypeName type, FieldWrapper field, List<ModelField> nestedFields) {
        super(type, field, nestedFields);
    }

    @Override
    public ListField create(TypeName type, FieldWrapper field, List<ModelField> nestedFields) {
        return new ListField(type, field, nestedFields);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ListRandomizer.class);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("2, 5");
    }
}
