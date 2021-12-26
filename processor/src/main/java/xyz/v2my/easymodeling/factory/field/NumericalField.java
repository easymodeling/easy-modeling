package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;

import java.util.Optional;

public class NumericalField extends PrimitiveField {

    private final Field field;

    public NumericalField(TypeName type, String name, Field field) {
        super(type, name);
        this.field = field;
    }

    @Override
    public String initializer() {
        return staticInitializer + "(" + max() + ")";
    }

    private String max() {
        return Optional.ofNullable(field).map(Field::max).filter(max -> Integer.MAX_VALUE != max).map(String::valueOf).orElse("");
    }
}
