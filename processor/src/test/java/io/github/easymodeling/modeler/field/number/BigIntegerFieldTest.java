package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.number.BigIntegerRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class BigIntegerFieldTest {

    @Nested
    class RangedBigIntegerFieldTest extends ModelFieldTest {

        @Override
        @BeforeEach
        protected void setUp() {
            fieldCustomization = FieldPatternFactory.one(FIELD_NAME).min(-2.).max(9.).build();
            typeName = ClassName.get(BigInteger.class);
            modelField = new BigIntegerField().create(fieldCustomization);
        }

        @Override
        protected void should_generate_initializer() {
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(BigIntegerRandomizer.class) + "(-2.0, 9.0)");
        }
    }

    @Nested
    class ConstantBigIntegerFieldTest extends ModelFieldTest {

        @Override
        @BeforeEach
        protected void setUp() {
            fieldCustomization = FieldPatternFactory.one(FIELD_NAME).constant(12.).build();
            typeName = ClassName.get(BigInteger.class);
            modelField = new BigIntegerField().create(fieldCustomization);
        }

        @Override
        protected void should_generate_initializer() {
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(BigIntegerRandomizer.class) + "(12L)");
        }
    }
}
