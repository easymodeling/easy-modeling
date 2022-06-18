package io.github.easymodeling.modeler.provider.mock;

import com.google.common.collect.Lists;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.mockito.Mockito;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

public class Factory {

    public static TypeMirror primitive(TypeName typeName) {
        final TypeMirror mockPrimitive = Mockito.mock(TypeMirror.class);
        doReturn(TypeKind.valueOf(typeName.toString().toUpperCase())).when(mockPrimitive).getKind();
        doReturn(typeName).when(mockPrimitive).accept(any(), any());
        return mockPrimitive;
    }

    public static TypeMirror clazz(Class<?> clazz) {
        final Element mockElement = mockElementOfKind(ElementKind.CLASS);
        doReturn(mockElementOfKind(ElementKind.PACKAGE)).when(mockElement).getEnclosingElement();

        final DeclaredType mockTypeMirror = Mockito.mock(DeclaredType.class);
        doReturn(TypeKind.DECLARED).when(mockTypeMirror).getKind();
        doReturn(TypeName.get(clazz)).when(mockTypeMirror).accept(any(), any());
        doReturn(mockElement).when(mockTypeMirror).asElement();
        doReturn(Lists.newArrayList()).when(mockTypeMirror).getTypeArguments();
        return mockTypeMirror;
    }

    public static TypeMirror container(Class<?> clazz, TypeMirror... elementType) {
        final Element mockElement = mockElementOfKind(ElementKind.CLASS);
        doReturn(mockElementOfKind(ElementKind.PACKAGE)).when(mockElement).getEnclosingElement();

        final DeclaredType mockTypeMirror = Mockito.mock(DeclaredType.class);
        doReturn(TypeKind.DECLARED).when(mockTypeMirror).getKind();
        doReturn(ParameterizedTypeName.get(ClassName.get(clazz), Arrays.stream(elementType).map(TypeName::get).toArray(TypeName[]::new)))
                .when(mockTypeMirror).accept(any(), any());
        doReturn(mockElement).when(mockTypeMirror).asElement();
        doReturn(Lists.newArrayList(elementType)).when(mockTypeMirror).getTypeArguments();
        return mockTypeMirror;
    }

    public static ArrayType array(TypeMirror componentType) {
        final ArrayType mockArrayType = Mockito.mock(ArrayType.class);
        doReturn(TypeKind.ARRAY).when(mockArrayType).getKind();
        doReturn(componentType).when(mockArrayType).getComponentType();
        doReturn(ArrayTypeName.of(TypeName.get(componentType))).when(mockArrayType).accept(any(), any());
        return mockArrayType;
    }

    private static Element mockElementOfKind(ElementKind elementKind) {
        final Element mockElement = Mockito.mock(Element.class);
        doReturn(elementKind).when(mockElement).getKind();
        return mockElement;
    }
}
