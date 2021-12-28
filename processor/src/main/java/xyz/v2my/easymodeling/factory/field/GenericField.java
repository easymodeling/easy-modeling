package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;

public class GenericField extends AbstractField {

    protected GenericField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public GenericField() {
    }

    @Override
    protected Class<? extends Randomizer> randomizer() {
        return null;
    }

    @Override
    public ModelField create(TypeName type, FieldWrapper field) {
        return new GenericField(type, field);
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("null");
    }
}
