package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.AbstractSetRandomizer;

import java.util.List;
import java.util.Set;

public class SetField extends AbstractCollectionField {

    public SetField(TypeName elementType, FieldWrapper field, List<ModelField> nestedFields) {
        super(ParameterizedTypeName.get(ClassName.get(Set.class), elementType), field, nestedFields);
        // TODO: 09.01.22 check elementType space size when constructing Field
        //  for example, if elementType is Integer between 0 and 3, when the set size is greater than 5,
        //  it will never generate the set.
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", AbstractSetRandomizer.class);
    }
}
