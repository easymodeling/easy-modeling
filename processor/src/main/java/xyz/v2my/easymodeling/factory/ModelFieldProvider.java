package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.ModelUniqueQueue;
import xyz.v2my.easymodeling.NamedModel;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.CustomField;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.UnknownField;
import xyz.v2my.easymodeling.factory.field.array.ArrayField;
import xyz.v2my.easymodeling.factory.field.array.PrimitiveArrayField;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static xyz.v2my.easymodeling.factory.ModelFieldRegistry.MODEL_FIELDS;

public class ModelFieldProvider {

    private final ModelUniqueQueue modelUniqueQueue;

    public ModelFieldProvider() {
        this.modelUniqueQueue = ModelUniqueQueue.instance();
    }

    private static final Map<TypeName, PlainField<?>> PLAIN_FIELDS = Arrays.stream(MODEL_FIELDS)
            .filter(PlainField.class::isInstance)
            .distinct()
            .map(f -> (PlainField<?>) f)
            .collect(Collectors.toMap(ModelField::type, f -> f));

    private static final Map<TypeName, Container> CONTAINERS = Arrays.stream(MODEL_FIELDS)
            .filter(Container.class::isInstance)
            .distinct()
            .map(Container.class::cast)
            .collect(Collectors.toMap(ModelField::type, f -> f));

    public ModelField provide(TypeName type, FieldPattern fieldPattern) {
        try {
            return findField(type, fieldPattern);
        } catch (FieldNotSupportedException e) {
            return new UnknownField(type, fieldPattern);
        }
    }

    private ModelField findField(TypeName type, FieldPattern fieldPattern) {
        if (type instanceof ArrayTypeName) {
            return arrayField((ArrayTypeName) type, fieldPattern);
        }
        if (type.isPrimitive() || type == TypeName.VOID) {
            return plainField(type, fieldPattern);
        }
        if (type instanceof ParameterizedTypeName) {
            return containerField((ParameterizedTypeName) type, fieldPattern);
        }
        if (!type.toString().startsWith("java.")) {
            modelUniqueQueue.add(new NamedModel(type.toString()));
            return new CustomField(type, fieldPattern);
        }
        return plainField(type, fieldPattern);
    }

    private ModelField nestedField(TypeName type, FieldPattern fieldPattern) {
        final ModelField nestedField = findField(type, fieldPattern);
        if (nestedField instanceof PrimitiveArrayField) {
            throw new FieldNotSupportedException();
        }
        return nestedField;
    }

    private Container arrayField(ArrayTypeName type, FieldPattern fieldPattern) {
        final TypeName rawType = rawType(type);
        if (rawType.isPrimitive()) {
            return new PrimitiveArrayField(type, fieldPattern, plainField(rawType, fieldPattern));
        }
        return new ArrayField(type, fieldPattern, nestedField(type.componentType, fieldPattern));
    }

    private ModelField containerField(ParameterizedTypeName parameterizedTypeName, FieldPattern fieldPattern) {
        final ClassName rawType = parameterizedTypeName.rawType;
        final ModelField[] modelFields = parameterizedTypeName.typeArguments.stream()
                .map(type -> nestedField(type, fieldPattern))
                .toArray(ModelField[]::new);
        try {
            return Optional.ofNullable(CONTAINERS.get(rawType)).orElseThrow(FieldNotSupportedException::new)
                    .create(fieldPattern, modelFields);
        } catch (ArrayIndexOutOfBoundsException obe) {
            throw new FieldNotSupportedException();
        }
    }

    private ModelField plainField(TypeName type, FieldPattern fieldPattern) {
        return Optional.ofNullable(PLAIN_FIELDS.get(type.box()))
                .orElseThrow(FieldNotSupportedException::new)
                .create(fieldPattern);
    }

    private TypeName rawType(TypeName type) {
        if (type instanceof ArrayTypeName) {
            return rawType(((ArrayTypeName) type).componentType);
        } else {
            return type;
        }
    }
}
