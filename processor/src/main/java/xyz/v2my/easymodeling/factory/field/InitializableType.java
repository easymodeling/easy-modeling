package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;

public interface InitializableType {

    CodeBlock initialValue();

    CodeBlock initializer();
}
