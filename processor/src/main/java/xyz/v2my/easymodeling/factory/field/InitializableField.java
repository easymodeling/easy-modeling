package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;

public interface InitializableField {

    CodeBlock initialValue();

    CodeBlock initializer();
}
