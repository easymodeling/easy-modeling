package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.UnknownField;
import xyz.v2my.easymodeling.factory.field.array.ArrayField;
import xyz.v2my.easymodeling.factory.field.array.PrimitiveArrayField;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static xyz.v2my.easymodeling.factory.ModelFields.MODEL_FIELDS;

public class ModelFieldProvider {

    private static final Map<TypeName, PlainField<?>> PLAIN_FIELDS = Arrays.stream(MODEL_FIELDS)
            .filter(PlainField.class::isInstance).map(f -> (PlainField<?>) f).collect(Collectors.toMap(ModelField::type, f -> f));

    private static final Map<TypeName, Container> CONTAINERS = Arrays.stream(MODEL_FIELDS)
            .filter(Container.class::isInstance).map(Container.class::cast).collect(Collectors.toMap(ModelField::type, f -> f));

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
        final ClassName rawType = parameterizedTypeName.rawType;
        final ModelField[] modelFields = parameterizedTypeName.typeArguments.stream()
                .map(type -> nestedField(type, field))
                .toArray(ModelField[]::new);
        try {
            return Optional.ofNullable(CONTAINERS.get(rawType)).orElseThrow(FieldNotSupportedException::new)
                    .create(field, modelFields);
        } catch (ArrayIndexOutOfBoundsException obe) {
            throw new FieldNotSupportedException();
        }
    }

    private PlainField<?> plainField(TypeName type, FieldWrapper field) {
        TypeName boxedType = type.isPrimitive() ? type.box() : type;
        return Optional.ofNullable(PLAIN_FIELDS.get(boxedType))
                .orElseThrow(FieldNotSupportedException::new)
                .create(field);
    }

    private TypeName rawType(TypeName type) {
        if (type instanceof ArrayTypeName) {
            return rawType(((ArrayTypeName) type).componentType);
        } else {
            return type;
        }
    }
}
