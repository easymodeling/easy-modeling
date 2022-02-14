package io.github.easymodeling.modeler.field.stream;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.modeler.field.number.LongField;
import io.github.easymodeling.modeler.field.number.NumericField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.stream.LongStreamRandomizer;

import java.util.stream.LongStream;

public class LongStreamField extends PrimitiveTypeStreamField<LongStream, Long> {

    public static final ClassName TYPE = ClassName.get(LongStream.class);

    public LongStreamField() {
        type = TYPE;
    }

    @Override
    public PlainField<LongStream> create(FieldPattern field, ModelField... valueFields) {
        return new LongStreamField(field);
    }

    private LongStreamField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<LongStream>> initializerType() {
        return LongStreamRandomizer.class;
    }

    @Override
    protected NumericField<Long> element() {
        return new LongField();
    }
}
