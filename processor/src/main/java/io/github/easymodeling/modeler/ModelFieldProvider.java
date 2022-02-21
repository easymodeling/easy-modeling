package io.github.easymodeling.modeler;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.ModelUniqueQueue;
import io.github.easymodeling.NamedModel;
import io.github.easymodeling.modeler.field.Container;
import io.github.easymodeling.modeler.field.CustomField;
import io.github.easymodeling.modeler.field.EnumField;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.UnknownField;
import io.github.easymodeling.modeler.field.array.ArrayField;
import io.github.easymodeling.modeler.field.array.PrimitiveArrayField;

import javax.lang.model.element.ElementKind;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.List;

import static io.github.easymodeling.log.ProcessorLogger.log;

public class ModelFieldProvider {

    private final ModelUniqueQueue modelUniqueQueue;

    public ModelFieldProvider() {
        this.modelUniqueQueue = ModelUniqueQueue.instance();
    }

    public ModelField provide(TypeMirror typeMirror, FieldPattern fieldPattern) {
        try {
            final ModelField field = findField(typeMirror, fieldPattern);
            log.debug("Model field created for [%s] is %s", fieldPattern.name(), field);
            return field;
        } catch (FieldNotSupportedException e) {
            return new UnknownField(TypeName.get(typeMirror), fieldPattern);
        }
    }

    private ModelField findField(TypeMirror typeMirror, FieldPattern fieldPattern) {
        log.debug("field [%s] with type %s as %s", fieldPattern.name(), typeMirror, typeMirror.getKind());

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
                return new EnumField(typeName, fieldPattern);
            }
            final String typeCanonicalName = typeName.toString();
            if (!typeCanonicalName.startsWith("java.")) {
                modelUniqueQueue.add(new NamedModel(typeCanonicalName));
                return new CustomField(typeName, fieldPattern);
            }
            final List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
            if (!typeArguments.isEmpty()) {
                log.debug("nested types: %s", typeArguments);
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
            return ModelFieldRegistry.container(rawType).orElseThrow(FieldNotSupportedException::new)
                    .create(fieldPattern, modelFields);
        } catch (ArrayIndexOutOfBoundsException obe) {
            throw new FieldNotSupportedException();
        }
    }

    private ModelField plainField(TypeMirror typeMirror, FieldPattern fieldPattern) {
        TypeName type = TypeName.get(typeMirror);
        return ModelFieldRegistry.plainField(type)
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
