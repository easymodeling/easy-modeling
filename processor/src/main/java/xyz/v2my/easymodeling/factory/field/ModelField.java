package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import xyz.v2my.easymodeling.factory.ImportGenerator;

public interface ModelField extends ImportGenerator {

    String initializer();

    ParameterSpec constructorParameter();

    CodeBlock constructorStatement();

    String constructorVariable();

    FieldSpec builderField();

    MethodSpec builderSetter(String builderTypeName);

}
