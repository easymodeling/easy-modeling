package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainType;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.primitive.BooleanRandomizer;

public class BooleanType extends PrimitiveType<Boolean> {

    private BooleanType(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public BooleanType() {
    }

    @Override
    protected Class<? extends Randomizer<Boolean>> initializerType() {
        return BooleanRandomizer.class;
    }

    @Override
    public PlainType<Boolean> create(TypeName type, FieldWrapper field) {
        return new BooleanType(type, field);
    }
}
