package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.HashSetRandomizer;

import java.util.HashSet;
import java.util.List;

public class HashSetField extends AbstractCollectionField {

    public HashSetField(TypeName elementType, FieldWrapper field, List<ModelField> nestedFields) {
        super(ParameterizedTypeName.get(ClassName.get(HashSet.class), elementType), field, nestedFields);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", HashSetRandomizer.class);
    }
}
