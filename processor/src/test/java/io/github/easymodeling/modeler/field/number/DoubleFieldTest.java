package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.number.DoubleRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

class DoubleFieldTest {

    @Nested
    class RangedDoubleFieldTest extends FieldTest {

        @Override
        @BeforeEach
        protected void setUp() {
            fieldPattern = FieldPatternFactory.one(FIELD_NAME).min(-2.).max(9.).build();
            typeName = ClassName.get(Double.class);
            modelField = new DoubleField().create(fieldPattern);
        }

        @Override
        protected void should_generate_initializer() {
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(DoubleRandomizer.class) + "(-2.0, 9.0)");
        }
    }

    @Nested
    class ConstantDoubleFieldTest extends FieldTest {

        @Override
        @BeforeEach
        protected void setUp() {
            fieldPattern = FieldPatternFactory.one(FIELD_NAME).constant(12.).build();
            typeName = ClassName.get(Double.class);
            modelField = new DoubleField().create(fieldPattern);
        }

        @Override
        protected void should_generate_initializer() {
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(DoubleRandomizer.class) + "(12.0)");
        }
    }

    @Nested
    class UnboxedDoubleFieldTest extends FieldTest {

        @Override
        @BeforeEach
        protected void setUp() {
            fieldPattern = FieldPatternFactory.one(FIELD_NAME).min(0.).max(2.).build();
            typeName = TypeName.DOUBLE;
            modelField = new DoubleField().create(fieldPattern);
        }

        @Override
        protected void should_generate_initializer() {
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(DoubleRandomizer.class) + "(0.0, 2.0)");
        }

        @Test
        final void should_generate_builder_field() {
            final FieldSpec field = modelField.field();

            assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
            assertThat(field.type).isEqualTo(ClassName.get(Double.class));
            assertThat(field.name).isEqualTo(FIELD_NAME);
        }

        @Test
        final void should_generate_builder_setter() {
            final MethodSpec setter = modelField.setter();

            assertThat(setter.name).isEqualTo(FIELD_NAME);
            assertThat(setter.returnType).hasToString("Builder");
            assertThat(setter.parameters).hasSize(1);
            assertThat(setter.parameters.get(0).name).isEqualTo(FIELD_NAME);
            assertThat(setter.parameters.get(0).type).isEqualTo(ClassName.get(Double.class));
            final String code = "" +
                    "this." + FIELD_NAME + " = " + FIELD_NAME + ";\n" +
                    "return this;\n";
            assertThat(setter.code).hasToString(code);
        }
    }
}
