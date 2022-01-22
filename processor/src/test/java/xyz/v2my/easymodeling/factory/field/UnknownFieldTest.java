package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UnknownFieldTest {

    private UnknownField unknownField;

    @BeforeEach
    void setUp() {
        unknownField = new UnknownField(TypeName.VOID, FieldWrapperFactory.any());
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
    void should_not_invoke_initializerType() {
        final Throwable throwable = catchThrowable(() -> unknownField.initializerType());

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void should_not_invoke_initializerParameter() {
        final Throwable throwable = catchThrowable(() -> unknownField.initializerParameter());

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void should_not_invoke_create() {
        final Throwable throwable = catchThrowable(() -> unknownField.create(FieldWrapperFactory.any()));

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }
}
