package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.primitive.BooleanRandomizer;

public class BooleanField extends PrimitiveField {

    public BooleanField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    protected Class<? extends Randomizer> randomizer() {
        return BooleanRandomizer.class;
    }
}
