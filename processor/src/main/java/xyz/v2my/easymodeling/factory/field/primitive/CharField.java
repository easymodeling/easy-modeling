package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.primitive.CharRandomizer;

public class CharField extends PrimitiveField {

    public CharField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    protected Class<? extends Randomizer> randomizer() {
        return CharRandomizer.class;
    }
}
