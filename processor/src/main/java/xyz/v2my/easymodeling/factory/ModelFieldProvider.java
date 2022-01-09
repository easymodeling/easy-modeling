package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.OptionalField;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.UnknownField;
import xyz.v2my.easymodeling.factory.field.array.ArrayField;
import xyz.v2my.easymodeling.factory.field.array.PrimitiveArrayField;
import xyz.v2my.easymodeling.factory.field.collection.ArrayListField;
import xyz.v2my.easymodeling.factory.field.collection.ListField;
import xyz.v2my.easymodeling.factory.field.datetime.InstantField;
import xyz.v2my.easymodeling.factory.field.number.ByteField;
import xyz.v2my.easymodeling.factory.field.number.DoubleField;
import xyz.v2my.easymodeling.factory.field.number.FloatField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.field.number.LongField;
import xyz.v2my.easymodeling.factory.field.number.ShortField;
import xyz.v2my.easymodeling.factory.field.primitive.BooleanField;
import xyz.v2my.easymodeling.factory.field.primitive.CharField;
import xyz.v2my.easymodeling.factory.field.string.StringBuilderField;
import xyz.v2my.easymodeling.factory.field.string.StringField;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelFieldProvider {

    private static final Map<TypeName, PlainField<?>> PLAIN_FIELDS = new HashMap<>();

    private static final Map<TypeName, Container> CONTAINER_FIELDS = new HashMap<>();

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
        CONTAINER_FIELDS.put(ClassName.get(List.class), new ListField());
        CONTAINER_FIELDS.put(ClassName.get(ArrayList.class), new ArrayListField());
    }

    public ModelField provide(TypeName type, FieldWrapper field) {
        try {
            return findField(type, field);
        } catch (FieldNotSupportedException e) {
            return new UnknownField(type, field);
        }
    }

    private ModelField findField(TypeName type, FieldWrapper field) {
        if (type instanceof ArrayTypeName) {
            return arrayField((ArrayTypeName) type, field);
        }
        if (type instanceof ParameterizedTypeName) {
            return containerField((ParameterizedTypeName) type, field);
        }
        return plainField(type, field);
    }

    private ModelField nestedField(TypeName type, FieldWrapper field) {
        final ModelField nestedField = findField(type, field);
        if (nestedField instanceof PrimitiveArrayField) {
            throw new FieldNotSupportedException();
        }
        return nestedField;
    }

    private Container arrayField(ArrayTypeName type, FieldWrapper field) {
        final TypeName rawType = rawType(type);
        if (rawType.isPrimitive()) {
            return new PrimitiveArrayField(type, field, plainField(rawType, field));
        }
        return new ArrayField(type, field, nestedField(type.componentType, field));
    }

    private Container containerField(ParameterizedTypeName parameterizedTypeName, FieldWrapper field) {
        final List<ModelField> nestedFields = parameterizedTypeName.typeArguments.stream()
                .map(type -> nestedField(type, field))
                .collect(Collectors.toList());
        return Optional.ofNullable(CONTAINER_FIELDS.get(parameterizedTypeName.rawType))
                .map(container -> container.create(parameterizedTypeName, field, nestedFields))
                .orElseThrow(FieldNotSupportedException::new);
    }

    private PlainField<?> plainField(TypeName type, FieldWrapper field) {
        return Optional.ofNullable(PLAIN_FIELDS.get(type))
                .map(modelField -> modelField.create(type, field))
                .orElseThrow(FieldNotSupportedException::new);
    }

    private TypeName rawType(TypeName type) {
        if (type instanceof ArrayTypeName) {
            return rawType(((ArrayTypeName) type).componentType);
        } else {
            return type;
        }
    }
}