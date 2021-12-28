package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;

public class GenericField extends AbstractField {

    public GenericField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    protected Class<? extends Randomizer> randomizer() {
        return null;
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("null");
    }
}
