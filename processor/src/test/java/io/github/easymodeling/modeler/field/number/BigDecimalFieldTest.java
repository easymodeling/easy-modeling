package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.number.BigDecimalRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalFieldTest {

    @Nested
    class RangedBigDecimalFieldTest extends ModelFieldTest {

        @Override
        @BeforeEach
        protected void setUp() {
            fieldCustomization = FieldPatternFactory.one(FIELD_NAME).min(-2.1).max(9.).build();
            typeName = ClassName.get(BigDecimal.class);
            modelField = new BigDecimalField().create(fieldCustomization);
        }

        @Override
        protected void should_generate_initializer() {
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(BigDecimalRandomizer.class) + "(-2.1, 9.0)");
        }
    }

    @Nested
    class ConstantBigDecimalFieldTest extends ModelFieldTest {

        @Override
        @BeforeEach
        protected void setUp() {
            fieldCustomization = FieldPatternFactory.one(FIELD_NAME).constant(120.).build();
            typeName = ClassName.get(BigDecimal.class);
            modelField = new BigDecimalField().create(fieldCustomization);
        }

        @Override
        protected void should_generate_initializer() {
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(BigDecimalRandomizer.class) + "(120.0)");
        }
    }
}
