package xyz.v2my.easymodeling.modeler.field.stream;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.modeler.field.PlainField;
import xyz.v2my.easymodeling.modeler.field.number.LongField;
import xyz.v2my.easymodeling.modeler.field.number.NumericField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.stream.LongStreamRandomizer;

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
