package xyz.v2my.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;

public interface ConstructorContributor {

    // TODO: 10.02.22 consider remove this base

    CodeBlock constructorStatement();
}
