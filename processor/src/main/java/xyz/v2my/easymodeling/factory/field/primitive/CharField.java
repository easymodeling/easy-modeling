package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.primitive.CharRandomizer;

public class CharField extends PrimitiveField<Character> {

    public CharField(FieldWrapper field) {
        super(ClassName.get(Character.class), field);
    }

    @Override
    protected Class<? extends Randomizer<Character>> initializerType() {
        return CharRandomizer.class;
    }
}
