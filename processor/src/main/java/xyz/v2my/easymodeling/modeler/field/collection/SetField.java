package xyz.v2my.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.SetRandomizer;

import java.util.Set;

public class SetField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(Set.class);

    public SetField() {
        this.type = TYPE;
    }

    @Override
    public SetField create(FieldPattern field, ModelField... nestedFields) {
        return new SetField(field, nestedFields[0]);
    }

    private SetField(FieldPattern field, ModelField nestedField) {
        super(TYPE, field, nestedField);
        // TODO: 09.01.22 check elementType space size when constructing Field
        //  for example, if elementType is Integer between 0 and 3, when the set size is greater than 5,
        //  it will never generate the set.
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", SetRandomizer.class);
    }
}
