package xyz.v2my.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.MapRandomizer;

import java.util.Map;

public class MapField extends AbstractMapField {

    public static final ClassName TYPE = ClassName.get(Map.class);

    public MapField() {
        this.type = TYPE;
    }

    @Override
    public MapField create(FieldPattern field, ModelField... nestedFields) {
        return new MapField(field, nestedFields[0], nestedFields[1]);
    }

    private MapField(FieldPattern field, ModelField keyField, ModelField valueField) {
        super(TYPE, field, keyField, valueField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", MapRandomizer.class);
    }
}
