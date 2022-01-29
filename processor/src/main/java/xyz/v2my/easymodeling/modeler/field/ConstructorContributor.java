package xyz.v2my.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterSpec;

public interface ConstructorContributor {

    ParameterSpec parameter();

    CodeBlock statement();
}
