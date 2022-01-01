package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.field.ArrayField;
import xyz.v2my.easymodeling.factory.field.GenericField;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.OptionalField;
import xyz.v2my.easymodeling.factory.field.datetime.InstantField;
import xyz.v2my.easymodeling.factory.field.numeric.ByteField;
import xyz.v2my.easymodeling.factory.field.numeric.DoubleField;
import xyz.v2my.easymodeling.factory.field.numeric.FloatField;
import xyz.v2my.easymodeling.factory.field.numeric.IntegerField;
import xyz.v2my.easymodeling.factory.field.numeric.LongField;
import xyz.v2my.easymodeling.factory.field.numeric.ShortField;
import xyz.v2my.easymodeling.factory.field.primitive.BooleanField;
import xyz.v2my.easymodeling.factory.field.primitive.CharField;
import xyz.v2my.easymodeling.factory.field.string.StringBuilderField;
import xyz.v2my.easymodeling.factory.field.string.StringField;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BuilderFieldProvider {

    private static final Map<TypeName, PlainField<?>> FIELD_MAP = new HashMap<>();

    static {
        FIELD_MAP.put(TypeName.BYTE, new ByteField());
        FIELD_MAP.put(TypeName.SHORT, new ShortField());
        FIELD_MAP.put(TypeName.INT, new IntegerField());
        FIELD_MAP.put(TypeName.LONG, new LongField());
        FIELD_MAP.put(TypeName.FLOAT, new FloatField());
        FIELD_MAP.put(TypeName.DOUBLE, new DoubleField());
        FIELD_MAP.put(TypeName.BOOLEAN, new BooleanField());
        FIELD_MAP.put(TypeName.CHAR, new CharField());
        FIELD_MAP.put(ClassName.get(Byte.class), new ByteField());
        FIELD_MAP.put(ClassName.get(Short.class), new ShortField());
        FIELD_MAP.put(ClassName.get(Integer.class), new IntegerField());
        FIELD_MAP.put(ClassName.get(Long.class), new LongField());
        FIELD_MAP.put(ClassName.get(Float.class), new FloatField());
        FIELD_MAP.put(ClassName.get(Double.class), new DoubleField());
        FIELD_MAP.put(ClassName.get(Boolean.class), new BooleanField());
        FIELD_MAP.put(ClassName.get(Character.class), new CharField());
        FIELD_MAP.put(ClassName.get(String.class), new StringField());
        FIELD_MAP.put(ClassName.get(StringBuilder.class), new StringBuilderField());
        FIELD_MAP.put(ClassName.get(Instant.class), new InstantField());
        FIELD_MAP.put(ClassName.get(Optional.class), new OptionalField<>());
    }

    public PlainField<?> provide(TypeName type, FieldWrapper field) {
        if (type instanceof ArrayTypeName) {
            return arrayField(type, field);
        }
        if (type instanceof ParameterizedTypeName) {
            return containerField((ParameterizedTypeName) type, field);
        }
        return typedField(type, field);
    }

    private PlainField<?> containerField(ParameterizedTypeName parameterizedTypeName, FieldWrapper field) {
        if (parameterizedTypeName.rawType.equals(ClassName.get(Optional.class))) {
            return optionalField(parameterizedTypeName, field);
        } else {
            return new GenericField();
        }
    }

    private PlainField<?> optionalField(ParameterizedTypeName type, FieldWrapper field) {
        final TypeName nestedType = type.typeArguments.get(0);
        final PlainField<?> nestedField = provide(nestedType, field);
        return new OptionalField<>(type, field, nestedField);
    }

    private ArrayField arrayField(TypeName type, FieldWrapper field) {
        final PlainField<?> elementField = typedFieldOfArray(((ArrayTypeName) type).componentType, field);
        return new ArrayField(type, field, elementField);
    }

    private PlainField<?> typedFieldOfArray(TypeName type, FieldWrapper field) {
        if (type instanceof ArrayTypeName) {
            return typedFieldOfArray(((ArrayTypeName) type).componentType, field);
        } else {
            return typedField(type, field);
        }
    }

    private PlainField<?> typedField(TypeName type, FieldWrapper field) {
        PlainField<?> modelField = FIELD_MAP.getOrDefault(type, new GenericField());
        return modelField.create(type, field);
    }
}
