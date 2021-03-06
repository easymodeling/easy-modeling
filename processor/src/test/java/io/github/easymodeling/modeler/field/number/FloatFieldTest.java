package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.number.FloatRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

class FloatFieldTest {

    @Nested
    class RangedFloatFieldTest extends ModelFieldTest {

        @Override
        @BeforeEach
        protected void setUp() {
            fieldCustomization = FieldPatternFactory.one(FIELD_NAME).min(-2.).max(9.).build();
            typeName = ClassName.get(Float.class);
            modelField = new FloatField().create(fieldCustomization);
        }

        @Override
        protected void should_generate_initializer() {
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(FloatRandomizer.class) + "(-2.0, 9.0)");
        }
    }

    @Nested
    class ConstantFloatFieldTest extends ModelFieldTest {

        @Override
        @BeforeEach
        protected void setUp() {
            fieldCustomization = FieldPatternFactory.one(FIELD_NAME).constant(12.).build();
            typeName = ClassName.get(Float.class);
            modelField = new FloatField().create(fieldCustomization);
        }

        @Override
        protected void should_generate_initializer() {
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(FloatRandomizer.class) + "(12.0F)");
        }
    }

    @Nested
    class UnboxedFloatFieldTest extends ModelFieldTest {

        @Override
        @BeforeEach
        protected void setUp() {
            fieldCustomization = FieldPatternFactory.one(FIELD_NAME).min(0.).max(2.).build();
            typeName = TypeName.FLOAT;
            modelField = new FloatField().create(fieldCustomization);
        }

        @Override
        protected void should_generate_initializer() {
            final CodeBlock initializer = modelField.initializer();

            assertThat(initializer).hasToString("new " + $(FloatRandomizer.class) + "(0.0, 2.0)");
        }

        @Test
        final void should_generate_builder_field() {
            final FieldSpec field = modelField.field();

            assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
            assertThat(field.type).isEqualTo(ClassName.get(Float.class));
            assertThat(field.name).isEqualTo(FIELD_NAME);
        }

        @Test
        final void should_generate_builder_setter() {
            final MethodSpec setter = modelField.setter();

            assertThat(setter.name).isEqualTo(FIELD_NAME);
            assertThat(setter.returnType).hasToString("Builder");
            assertThat(setter.parameters).hasSize(1);
            assertThat(setter.parameters.get(0).name).isEqualTo(FIELD_NAME);
            assertThat(setter.parameters.get(0).type).isEqualTo(ClassName.get(Float.class));
            final String code = "" +
                    "this." + FIELD_NAME + " = " + FIELD_NAME + ";\n" +
                    "return this;\n";
            assertThat(setter.code).hasToString(code);
        }
    }
}
