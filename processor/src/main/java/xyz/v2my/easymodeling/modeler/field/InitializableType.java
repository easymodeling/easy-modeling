package xyz.v2my.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;

public interface InitializableType {

    CodeBlock initialValue();

    CodeBlock initializer();
}
