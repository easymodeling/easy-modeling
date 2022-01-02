package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.field.ArrayType;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.OptionalType;
import xyz.v2my.easymodeling.factory.field.PlainType;
import xyz.v2my.easymodeling.factory.field.Type;
import xyz.v2my.easymodeling.factory.field.UnknownContainer;
import xyz.v2my.easymodeling.factory.field.UnknownType;
import xyz.v2my.easymodeling.factory.field.datetime.InstantType;
import xyz.v2my.easymodeling.factory.field.numeric.ByteType;
import xyz.v2my.easymodeling.factory.field.numeric.DoubleType;
import xyz.v2my.easymodeling.factory.field.numeric.FloatType;
import xyz.v2my.easymodeling.factory.field.numeric.IntegerType;
import xyz.v2my.easymodeling.factory.field.numeric.LongType;
import xyz.v2my.easymodeling.factory.field.numeric.ShortType;
import xyz.v2my.easymodeling.factory.field.primitive.BooleanType;
import xyz.v2my.easymodeling.factory.field.primitive.CharType;
import xyz.v2my.easymodeling.factory.field.string.StringBuilderType;
import xyz.v2my.easymodeling.factory.field.string.StringType;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelFieldProvider {

    private static final Map<TypeName, PlainType<?>> PLAIN_FIELDS = new HashMap<>();

    private static final Map<TypeName, Container<?>> CONTAINER_FIELDS = new HashMap<>();

    static {
        PLAIN_FIELDS.put(TypeName.BYTE, new ByteType());
        PLAIN_FIELDS.put(TypeName.SHORT, new ShortType());
        PLAIN_FIELDS.put(TypeName.INT, new IntegerType());
        PLAIN_FIELDS.put(TypeName.LONG, new LongType());
        PLAIN_FIELDS.put(TypeName.FLOAT, new FloatType());
        PLAIN_FIELDS.put(TypeName.DOUBLE, new DoubleType());
        PLAIN_FIELDS.put(TypeName.BOOLEAN, new BooleanType());
        PLAIN_FIELDS.put(TypeName.CHAR, new CharType());
        PLAIN_FIELDS.put(ClassName.get(Byte.class), new ByteType());
        PLAIN_FIELDS.put(ClassName.get(Short.class), new ShortType());
        PLAIN_FIELDS.put(ClassName.get(Integer.class), new IntegerType());
        PLAIN_FIELDS.put(ClassName.get(Long.class), new LongType());
        PLAIN_FIELDS.put(ClassName.get(Float.class), new FloatType());
        PLAIN_FIELDS.put(ClassName.get(Double.class), new DoubleType());
        PLAIN_FIELDS.put(ClassName.get(Boolean.class), new BooleanType());
        PLAIN_FIELDS.put(ClassName.get(Character.class), new CharType());
        PLAIN_FIELDS.put(ClassName.get(String.class), new StringType());
        PLAIN_FIELDS.put(ClassName.get(StringBuilder.class), new StringBuilderType());
        PLAIN_FIELDS.put(ClassName.get(Instant.class), new InstantType());

        CONTAINER_FIELDS.put(ClassName.get(Optional.class), new OptionalType());
    }

    public Type provide(TypeName type, FieldWrapper field) {
        if (type instanceof ArrayTypeName) {
            return arrayField(type, field);
        }
        if (type instanceof ParameterizedTypeName) {
            return containerField((ParameterizedTypeName) type, field);
        }
        return typedField(type, field);
    }

    private Type containerField(ParameterizedTypeName parameterizedTypeName, FieldWrapper field) {
        final List<Type> nestedFields = parameterizedTypeName.typeArguments.stream().map(t -> provide(t, field)).collect(Collectors.toList());
        return CONTAINER_FIELDS.getOrDefault(parameterizedTypeName.rawType, new UnknownContainer()).create(parameterizedTypeName, field, nestedFields);
    }

    private ArrayType arrayField(TypeName type, FieldWrapper field) {
        final PlainType<?> elementField = typedFieldOfArray(((ArrayTypeName) type).componentType, field);
        return new ArrayType(type, field, elementField);
    }

    private PlainType<?> typedFieldOfArray(TypeName type, FieldWrapper field) {
        if (type instanceof ArrayTypeName) {
            return typedFieldOfArray(((ArrayTypeName) type).componentType, field);
        } else {
            return typedField(type, field);
        }
    }

    private PlainType<?> typedField(TypeName type, FieldWrapper field) {
        PlainType<?> modelField = PLAIN_FIELDS.getOrDefault(type, new UnknownType());
        return modelField.create(type, field);
    }
}
