package io.github.easymodeling.modeler.field.stream;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.field.number.NumericField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.stream.IntStreamRandomizer;

import java.util.stream.IntStream;

public class IntStreamField extends PrimitiveTypeStreamField<IntStream, Integer> {

    public static final ClassName TYPE = ClassName.get(IntStream.class);

    public IntStreamField() {
        type = TYPE;
    }

    @Override
    public PlainField<IntStream> create(FieldPattern field, ModelField... valueFields) {
        return new IntStreamField(field);
    }

    private IntStreamField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<IntStream>> initializerType() {
        return IntStreamRandomizer.class;
    }

    @Override
    protected NumericField<Integer> element() {
        return new IntegerField();
    }
}
