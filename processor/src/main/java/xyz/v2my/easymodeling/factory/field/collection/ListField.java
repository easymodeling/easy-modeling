package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.ListRandomizer;

import java.util.List;

public class ListField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(List.class);

    public ListField() {
        this.type = TYPE;
    }

    @Override
    public ListField create(FieldWrapper field, ModelField... nestedFields) {
        return new ListField(field, nestedFields[0]);
    }

    private ListField(FieldWrapper field, ModelField nestedFields) {
        super(TYPE, field, nestedFields);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ListRandomizer.class);
    }
}
