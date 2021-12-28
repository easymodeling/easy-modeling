package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.primitive.CharRandomizer;

public class CharField extends PrimitiveField {

    protected CharField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public CharField() {
    }

    @Override
    public ModelField create(TypeName type, FieldWrapper field) {
        return new CharField(type, field);
    }

    @Override
    protected Class<? extends Randomizer> randomizer() {
        return CharRandomizer.class;
    }
}
