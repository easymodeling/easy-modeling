package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UnknownFieldTest {

    private UnknownField unknownField;

    @BeforeEach
    void setUp() {
        unknownField = new UnknownField(TypeName.VOID, FieldPatternFactory.any());
    }

    @Test
    void should_generate_null_as_init_value() {
        final CodeBlock initialValue = unknownField.initialValue();

        assertThat(initialValue).hasToString("null");
    }

    @Test
    void should_not_invoke_initializer() {
        final Throwable throwable = catchThrowable(() -> unknownField.initializer());

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void should_not_invoke_create() {
        final Throwable throwable = catchThrowable(() -> unknownField.create(FieldPatternFactory.any()));

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }
}
