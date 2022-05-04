package io.github.easymodeling.modeler;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import io.github.easymodeling.processor.GenerationPatterns;
import io.github.easymodeling.modeler.helper.ModelWrapperFactory;
import io.github.easymodeling.randomizer.ModelCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

class ModelerGeneratorTest {

    private ModelerGenerator modelerGenerator;

    @BeforeEach
    void setUp() {
        final ModelWrapper modelWrapper = ModelWrapperFactory.create(SomeClass.class).build();
        modelerGenerator = new ModelerGenerator(modelWrapper);
    }

    @Test
    void should_create_modeler_with_given_class_name() {
        final TypeSpec type = modelerGenerator.createType();

        assertThat(type.name).isEqualTo(SomeClass.class.getSimpleName() + "Modeler");
    }

    @Test
    void should_generate_modeler_methods() {
        final TypeSpec type = modelerGenerator.createType();

        assertThat(type.methodSpecs).hasSize(4);
    }

    @Test
    void should_generate_builder_inner_class() {
        final TypeSpec type = modelerGenerator.createType();

        assertThat(type.typeSpecs).hasSize(1);
    }

    @Nested
    class StaticNextMethodTest {

        private MethodSpec nextMethod;

        @BeforeEach
        void setUp() {
            final TypeSpec type = modelerGenerator.createType();
            nextMethod = type.methodSpecs.stream()
                    .filter(methodSpec -> methodSpec.name.equals(GenerationPatterns.STATIC_NEXT_METHOD_NAME))
                    .findFirst().orElseThrow(() -> new RuntimeException("static next method not found"));
        }

        @Test
        void should_be_public_static_and_named_as_next() {
            assertThat(nextMethod.modifiers).containsExactly(Modifier.PUBLIC, Modifier.STATIC);
            assertThat(nextMethod.name).isEqualTo(GenerationPatterns.STATIC_NEXT_METHOD_NAME);
        }

        @Test
        void should_return_given_type_and_take_no_parameter() {
            assertThat(nextMethod.returnType).isEqualTo(ClassName.get(SomeClass.class));
            assertThat(nextMethod.parameters).isEmpty();
        }

        @Test
        void should_invoke_builder_build() {
            assertThat(nextMethod.code).hasToString("return new SomeClassModeler().next(null);\n");
        }
    }

    @Nested
    class StaticBuilderMethodTest {

        private MethodSpec nextMethod;

        @BeforeEach
        void setUp() {
            final TypeSpec type = modelerGenerator.createType();
            nextMethod = type.methodSpecs.stream()
                    .filter(methodSpec -> methodSpec.name.equals(GenerationPatterns.STATIC_BUILDER_METHOD_NAME))
                    .findFirst().orElseThrow(() -> new RuntimeException("static builder method not found"));
        }

        @Test
        void should_be_public_static_and_named_as_builder() {
            assertThat(nextMethod.modifiers).containsExactly(Modifier.PUBLIC, Modifier.STATIC);
            assertThat(nextMethod.name).isEqualTo(GenerationPatterns.STATIC_BUILDER_METHOD_NAME);
        }

        @Test
        void should_return_given_type_and_take_no_parameter() {
            assertThat(nextMethod.returnType).hasToString(GenerationPatterns.BUILDER_CLASS_NAME);
            assertThat(nextMethod.parameters).isEmpty();
        }

        @Test
        void should_invoke_constructor_of_builder() {
            assertThat(nextMethod.code).hasToString("return new SomeClassModeler.Builder(next());\n");
        }
    }

    @Nested
    class MemberTypeMethodTest {

        private MethodSpec typeMethod;

        @BeforeEach
        void setUp() {
            final TypeSpec type = modelerGenerator.createType();
            typeMethod = type.methodSpecs.stream()
                    .filter(methodSpec -> methodSpec.name.equals(GenerationPatterns.TYPE_METHOD_NAME))
                    .findFirst().orElseThrow(() -> new RuntimeException("member next method not found"));
        }

        @Test
        void should_be_protected_and_named_as_type() {
            assertThat(typeMethod.modifiers).containsExactly(Modifier.PROTECTED);
            assertThat(typeMethod.name).isEqualTo(GenerationPatterns.TYPE_METHOD_NAME);
            assertThat(typeMethod.annotations).hasSize(1).containsExactly(AnnotationSpec.builder(Override.class).build());
        }

        @Test
        void should_return_given_type_and_take_no_parameter() {
            assertThat(typeMethod.returnType).isEqualTo(ParameterizedTypeName.get(Class.class, SomeClass.class));
            assertThat(typeMethod.parameters).isEmpty();
        }

        @Test
        void should_return_type() {
            assertThat(typeMethod.code).hasToString("return " + SomeClass.class.getCanonicalName() + ".class;\n");
        }
    }

    @Nested
    class MemberPopulateMethodTest {

        private MethodSpec populateMethod;

        @BeforeEach
        void setUp() {
            final TypeSpec type = modelerGenerator.createType();
            populateMethod = type.methodSpecs.stream()
                    .filter(methodSpec -> methodSpec.name.equals(GenerationPatterns.MEMBER_POPULATE_METHOD_NAME))
                    .findFirst().orElseThrow(() -> new RuntimeException("member populate method not found"));
        }

        @Test
        void should_be_protected_and_named_as_populate() {
            assertThat(populateMethod.modifiers).containsExactly(Modifier.PROTECTED);
            assertThat(populateMethod.name).isEqualTo(GenerationPatterns.MEMBER_POPULATE_METHOD_NAME);
        }

        @Test
        void should_take_two_parameter_and_return_void() {
            assertThat(populateMethod.returnType).isEqualTo(TypeName.VOID);
            assertThat(populateMethod.parameters).hasSize(2);
            assertThat(populateMethod.parameters.get(0).type).isEqualTo(ClassName.get(SomeClass.class));
            assertThat(populateMethod.parameters.get(0).name).isEqualTo("model");
            assertThat(populateMethod.parameters.get(1).type).isEqualTo(ClassName.get(ModelCache.class));
            assertThat(populateMethod.parameters.get(1).name).isEqualTo("modelCache");
        }
    }

    @Nested
    class BuilderGeneratorTest {

        TypeSpec builder;

        @BeforeEach
        void setUp() {
            final TypeSpec type = modelerGenerator.createType();
            builder = type.typeSpecs.stream().findAny().orElseThrow(() -> new RuntimeException("builder class not found"));
        }

        @Test
        void should_be_public_static_with_name_builder() {
            assertThat(builder.name).isEqualTo("Builder");
            assertThat(builder.modifiers).containsExactly(Modifier.PUBLIC, Modifier.STATIC);
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    class SomeClass {

        private Integer id;

        private String name;
    }
}
