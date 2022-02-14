package io.github.easymodeling.modeler.provider.mock;

import com.squareup.javapoet.TypeName;

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class Factory {

    public static TypeMirror primitive(TypeName typeName) {
        return new TypeMirrorMock(TypeKind.valueOf(typeName.toString().toUpperCase()), typeName);
    }

    public static TypeMirror clazz(Class<?> clazz) {
        return new DeclaredTypeMock(clazz);
    }

    public static TypeMirror container(Class<?> clazz, TypeMirror... elementType) {
        return new DeclaredTypeMock(clazz, elementType);
    }

    public static ArrayType array(TypeMirror componentType) {
        return new ArrayTypeMock(componentType);
    }
}
