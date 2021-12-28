package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public interface ModelField {

    ModelField create(TypeName type, FieldWrapper field);

    CodeBlock initializer();

    ParameterSpec constructorParameter();

    CodeBlock constructorStatement();

    String constructorVariable();

    FieldSpec builderField();

    MethodSpec builderSetter(String builderTypeName);

}
