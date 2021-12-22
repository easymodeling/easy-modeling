package xyz.v2my.easymodeling;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

public interface BuilderField {

    String initializer();

    ParameterSpec constructorParameter();

    CodeBlock constructorStatement();

    String constructorVariable();

    FieldSpec builderField();

    MethodSpec builderSetter(String builderTypeName);

}
