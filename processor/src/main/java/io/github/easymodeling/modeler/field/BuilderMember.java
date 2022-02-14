package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

public interface BuilderMember {

    FieldSpec field();

    MethodSpec setter();
}
