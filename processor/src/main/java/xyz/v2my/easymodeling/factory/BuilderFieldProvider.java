package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.field.ArrayField;
import xyz.v2my.easymodeling.factory.field.ContainerField;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.OptionalField;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.UnknownField;
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
import java.util.stream.Collectors;

public class BuilderFieldProvider {

    private static final Map<TypeName, PlainField<?>> PLAIN_FIELDS = new HashMap<>();

    private static final Map<TypeName, ContainerField<?>> CONTAINER_FIELDS = new HashMap<>();

    static {
        PLAIN_FIELDS.put(TypeName.BYTE, new ByteField());
        PLAIN_FIELDS.put(TypeName.SHORT, new ShortField());
        PLAIN_FIELDS.put(TypeName.INT, new IntegerField());
        PLAIN_FIELDS.put(TypeName.LONG, new LongField());
        PLAIN_FIELDS.put(TypeName.FLOAT, new FloatField());
        PLAIN_FIELDS.put(TypeName.DOUBLE, new DoubleField());
        PLAIN_FIELDS.put(TypeName.BOOLEAN, new BooleanField());
        PLAIN_FIELDS.put(TypeName.CHAR, new CharField());
        PLAIN_FIELDS.put(ClassName.get(Byte.class), new ByteField());
        PLAIN_FIELDS.put(ClassName.get(Short.class), new ShortField());
        PLAIN_FIELDS.put(ClassName.get(Integer.class), new IntegerField());
        PLAIN_FIELDS.put(ClassName.get(Long.class), new LongField());
        PLAIN_FIELDS.put(ClassName.get(Float.class), new FloatField());
        PLAIN_FIELDS.put(ClassName.get(Double.class), new DoubleField());
        PLAIN_FIELDS.put(ClassName.get(Boolean.class), new BooleanField());
        PLAIN_FIELDS.put(ClassName.get(Character.class), new CharField());
        PLAIN_FIELDS.put(ClassName.get(String.class), new StringField());
        PLAIN_FIELDS.put(ClassName.get(StringBuilder.class), new StringBuilderField());
        PLAIN_FIELDS.put(ClassName.get(Instant.class), new InstantField());

        CONTAINER_FIELDS.put(ClassName.get(Optional.class), new OptionalField());
    }

    public ModelField provide(TypeName type, FieldWrapper field) {
        if (type instanceof ArrayTypeName) {
            return arrayField(type, field);
        }
        if (type instanceof ParameterizedTypeName) {
            return containerField((ParameterizedTypeName) type, field);
        }
        return typedField(type, field);
    }

    private ModelField containerField(ParameterizedTypeName parameterizedTypeName, FieldWrapper field) {
        if (parameterizedTypeName.rawType.equals(ClassName.get(Optional.class))) {
            return optionalField(parameterizedTypeName, field);
        } else {
            return new UnknownField();
        }
    }

    private ModelField optionalField(ParameterizedTypeName type, FieldWrapper field) {
        final ContainerField<?> containerField = CONTAINER_FIELDS.get(type.rawType);
        return containerField.create(
                type, field, type.typeArguments.stream().map(t -> provide(t, field)).collect(Collectors.toList()));
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
        // FIXME: 01.01.22
        PlainField<?> modelField = PLAIN_FIELDS.getOrDefault(type, new UnknownField());
        return modelField.create(type, field);
    }
}
