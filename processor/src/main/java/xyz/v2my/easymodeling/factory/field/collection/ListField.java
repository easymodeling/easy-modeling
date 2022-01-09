package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.ListRandomizer;

import java.util.List;

public class ListField extends AbstractCollectionField {

    public ListField(TypeName elementType, FieldWrapper field, List<ModelField> nestedFields) {
        super(ParameterizedTypeName.get(ClassName.get(List.class), elementType), field, nestedFields);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ListRandomizer.class);
    }
}
