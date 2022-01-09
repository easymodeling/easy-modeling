package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.primitive.BooleanRandomizer;

public class BooleanField extends PrimitiveField<Boolean> {

    public BooleanField(FieldWrapper field) {
        super(ClassName.get(Boolean.class), field);
    }

    @Override
    protected Class<? extends Randomizer<Boolean>> initializerType() {
        return BooleanRandomizer.class;
    }
}
