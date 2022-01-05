package xyz.v2my.easymodeling.factory.field.array;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.array.ArrayRandomizer;

import java.util.Collections;
import java.util.List;

public class ArrayField extends Container {

    public ArrayField(TypeName type, FieldWrapper field, ModelField elementField) {
        super(type, field, Collections.singletonList(elementField));
    }

    @Override
    public Container create(TypeName type, FieldWrapper field, List<ModelField> nestedFields) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ArrayRandomizer.class);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("$L, $L", minLength(), maxLength());
    }

    private int maxLength() {
        return field.maxLength().orElse(20);
    }

    private int minLength() {
        return field.minLength().orElse(1);
    }
}
