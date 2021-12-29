package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.AbstractField;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.InstantRandomizer;

public class InstantField extends AbstractField {

    public InstantField() {
    }

    protected InstantField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public ModelField create(TypeName type, FieldWrapper field) {
        return new InstantField(type, field);
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T().next()", randomizer());
    }

    @Override
    protected Class<? extends Randomizer<?>> randomizer() {
        return InstantRandomizer.class;
    }
}
