package xyz.v2my.easymodeling.factory.field.stream;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.number.LongField;
import xyz.v2my.easymodeling.factory.field.number.NumericField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.stream.LongStreamRandomizer;

import java.util.stream.LongStream;

public class LongStreamField extends PrimitiveTypeStreamField<LongStream, Long> {

    public static final ClassName TYPE = ClassName.get(LongStream.class);

    public LongStreamField() {
        type = TYPE;
    }

    @Override
    public PlainField<LongStream> create(FieldWrapper field) {
        return new LongStreamField(field);
    }

    private LongStreamField(FieldWrapper field) {
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
