package io.github.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ModelFieldRegistryTest {

    @ParameterizedTest
    @MethodSource("typeNameProvider")
    void should_be_true_for_declared_types(TypeName typeName) {
        final boolean typeContains = ModelFieldRegistry.basicTypeContains(typeName);

        assertThat(typeContains).isTrue();
    }

    @Test
    void should_be_false_for_undeclared_types() {
        final boolean typeContains = ModelFieldRegistry.basicTypeContains(ClassName.get(ClassName.class));

        assertThat(typeContains).isFalse();
    }

    public static Stream<Arguments> typeNameProvider() {
        return Stream.of(
                Arguments.of(ClassName.get(String.class)),
                Arguments.of(ClassName.get(Double.class)),
                Arguments.of(ClassName.get(BigDecimal.class))
        );
    }
}
