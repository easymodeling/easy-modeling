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
        final ClassName type = parameterizedTypeName.rawType;
        if (ClassName.get(Optional.class).equals(type)) {
            return new OptionalField(parameterizedTypeName, field, nestedFields);
        }
        if (ClassName.get(List.class).equals(type)) {
            return new ListField(parameterizedTypeName, field, nestedFields);
        }
        if (ClassName.get(ArrayList.class).equals(type)) {
            return new ArrayListField(parameterizedTypeName, field, nestedFields);
        }
        throw new FieldNotSupportedException();
    }

    private PlainField<?> plainField(TypeName type, FieldWrapper field) {
        if (TypeName.BYTE.equals(type) || ClassName.get(Byte.class).equals(type)) {
            return new ByteField(type, field);
        }
        if (TypeName.SHORT.equals(type) || ClassName.get(Short.class).equals(type)) {
            return new ShortField(type, field);
        }
        if (TypeName.INT.equals(type) || ClassName.get(Integer.class).equals(type)) {
            return new IntegerField(type, field);
        }
        if (TypeName.LONG.equals(type) || ClassName.get(Long.class).equals(type)) {
            return new LongField(type, field);
        }
        if (TypeName.FLOAT.equals(type) || ClassName.get(Float.class).equals(type)) {
            return new FloatField(type, field);
        }
        if (TypeName.DOUBLE.equals(type) || ClassName.get(Double.class).equals(type)) {
            return new DoubleField(type, field);
        }
        if (TypeName.BOOLEAN.equals(type) || ClassName.get(Boolean.class).equals(type)) {
            return new BooleanField(type, field);
        }
        if (TypeName.CHAR.equals(type) || ClassName.get(Character.class).equals(type)) {
            return new CharField(type, field);
        }
        if (type.equals(ClassName.get(String.class))) {
            return new StringField(type, field);
        }
        if (type.equals(ClassName.get(StringBuilder.class))) {
            return new StringBuilderField(type, field);
        }
        if (type.equals(ClassName.get(Instant.class))) {
            return new InstantField(type, field);
        }
        throw new FieldNotSupportedException();
    }

    private TypeName rawType(TypeName type) {
        if (type instanceof ArrayTypeName) {
            return rawType(((ArrayTypeName) type).componentType);
        } else {
            return type;
        }
    }
}
