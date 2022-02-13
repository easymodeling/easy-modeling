package xyz.v2my.easymodeling.modeler;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.ModelUniqueQueue;
import xyz.v2my.easymodeling.NamedModel;
import xyz.v2my.easymodeling.modeler.field.Container;
import xyz.v2my.easymodeling.modeler.field.CustomField;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.modeler.field.PlainField;
import xyz.v2my.easymodeling.modeler.field.UnknownField;
import xyz.v2my.easymodeling.modeler.field.array.ArrayField;
import xyz.v2my.easymodeling.modeler.field.array.PrimitiveArrayField;

import javax.lang.model.element.ElementKind;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static xyz.v2my.easymodeling.modeler.ModelFieldRegistry.MODEL_FIELDS;

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

    public ModelField provide(TypeMirror typeMirror, FieldPattern fieldPattern) {
        try {
            return findField(typeMirror, fieldPattern);
        } catch (FieldNotSupportedException e) {
            return new UnknownField(TypeName.get(typeMirror), fieldPattern);
        }
    }

    private ModelField findField(TypeMirror typeMirror, FieldPattern fieldPattern) {
        if (typeMirror.getKind().isPrimitive()) {
            return plainField(typeMirror, fieldPattern);
        }
        if (typeMirror.getKind().equals(TypeKind.ARRAY)) {
            return arrayField((ArrayType) typeMirror, fieldPattern);
        }
        if (typeMirror.getKind().equals(TypeKind.DECLARED)) {
            final DeclaredType declaredType = (DeclaredType) typeMirror;
            final TypeName typeName = TypeName.get(declaredType);
            if (declaredType.asElement().getKind().equals(ElementKind.ENUM)) {
                // TODO: 12.02.22 support enum
                throw new FieldNotSupportedException();
            }
            final String typeCanonicalName = typeName.toString();
            if (!typeCanonicalName.startsWith("java.")) {
                modelUniqueQueue.add(new NamedModel(typeCanonicalName));
                return new CustomField(typeName, fieldPattern);
            }
            final List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
            if (!typeArguments.isEmpty()) {
                return containerField(declaredType, fieldPattern);
            }
            return plainField(typeMirror, fieldPattern);
        }
        throw new FieldNotSupportedException();
    }

    private ModelField nestedField(TypeMirror typeMirror, FieldPattern fieldPattern) {
        final ModelField nestedField = findField(typeMirror, fieldPattern);
        if (nestedField instanceof PrimitiveArrayField) {
            throw new FieldNotSupportedException();
        }
        return nestedField;
    }

    private Container arrayField(ArrayType typeMirror, FieldPattern fieldPattern) {
        final ArrayTypeName arrayTypeName = ArrayTypeName.get(typeMirror);
        final TypeMirror rawType = rawType(typeMirror);
        if (rawType.getKind().isPrimitive()) {
            return new PrimitiveArrayField(arrayTypeName, fieldPattern, plainField(rawType, fieldPattern));
        }
        return new ArrayField(arrayTypeName, fieldPattern, nestedField(typeMirror.getComponentType(), fieldPattern));
    }

    private ModelField containerField(DeclaredType parameterizedTypeMirror, FieldPattern fieldPattern) {
        final TypeName rawType = ((ParameterizedTypeName) TypeName.get(parameterizedTypeMirror)).rawType;
        final ModelField[] modelFields = parameterizedTypeMirror.getTypeArguments().stream()
                .map(typeMirror -> nestedField(typeMirror, fieldPattern))
                .toArray(ModelField[]::new);
        try {
            return Optional.ofNullable(CONTAINERS.get(rawType)).orElseThrow(FieldNotSupportedException::new)
                    .create(fieldPattern, modelFields);
        } catch (ArrayIndexOutOfBoundsException obe) {
            throw new FieldNotSupportedException();
        }
    }

    private ModelField plainField(TypeMirror typeMirror, FieldPattern fieldPattern) {
        TypeName type = TypeName.get(typeMirror);
        return Optional.ofNullable(PLAIN_FIELDS.get(type.box()))
                .orElseThrow(FieldNotSupportedException::new)
                .create(fieldPattern);
    }

    private TypeMirror rawType(TypeMirror typeMirror) {
        if (typeMirror.getKind().equals(TypeKind.ARRAY)) {
            return rawType(((ArrayType) typeMirror).getComponentType());
        } else {
            return typeMirror;
        }
    }
}