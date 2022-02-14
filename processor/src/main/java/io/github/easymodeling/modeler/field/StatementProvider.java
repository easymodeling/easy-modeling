package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;

public interface StatementProvider {

    CodeBlock constructorStatement();

    CodeBlock populateStatement();

    CodeBlock buildStatement();
}
