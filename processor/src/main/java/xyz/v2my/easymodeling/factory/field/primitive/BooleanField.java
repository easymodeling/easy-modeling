package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.primitive.BooleanRandomizer;

public class BooleanField extends PrimitiveField<Boolean> {

    private BooleanField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public BooleanField() {
    }

    @Override
    protected Class<? extends Randomizer<Boolean>> initializerType() {
        return BooleanRandomizer.class;
    }

    @Override
    public PlainField<Boolean> create(TypeName type, FieldWrapper field) {
        return new BooleanField(type, field);
    }
}
