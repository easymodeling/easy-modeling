package xyz.v2my.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.modeler.helper.ModelWrapperFactory;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.v2my.easymodeling.GenerationPatterns.BUILDER_CLASS_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.MEMBER_BUILDER_METHOD_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.MEMBER_NEXT_METHOD_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.STATIC_BUILDER_METHOD_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.STATIC_NEXT_METHOD_NAME;

class ModelerGeneratorTest {

    private ModelerGenerator modelerGenerator;

    @BeforeEach
    void setUp() {
        final ModelWrapper modelWrapper = ModelWrapperFactory.create(SomeClass.class).build();
        modelerGenerator = new ModelerGenerator(modelWrapper);
    }

    @Test
    void should_create_factory_with_given_class_name() {
        final TypeSpec type = modelerGenerator.createType();

        assertThat(type.name).isEqualTo(SomeClass.class.getSimpleName() + "Modeler");
    }

    @Test
    void should_generate_factory_methods() {
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
                    .filter(methodSpec -> methodSpec.name.equals(STATIC_NEXT_METHOD_NAME))
                    .findFirst().orElseThrow(() -> new RuntimeException("static next method not found"));
        }

        @Test
        void should_be_public_static_and_named_as_next() {
            assertThat(nextMethod.modifiers).containsExactly(Modifier.PUBLIC, Modifier.STATIC);
            assertThat(nextMethod.name).isEqualTo(STATIC_NEXT_METHOD_NAME);
        }

        @Test
        void should_return_given_type_and_take_no_parameter() {
            assertThat(nextMethod.returnType).isEqualTo(ClassName.get(SomeClass.class));
            assertThat(nextMethod.parameters).isEmpty();
        }

        @Test
        void should_invoke_builder_build() {
            assertThat(nextMethod.code).hasToString("return new SomeClassModeler().$next();\n");
        }
    }

    @Nested
    class StaticBuilderMethodTest {

        private MethodSpec nextMethod;

        @BeforeEach
        void setUp() {
            final TypeSpec type = modelerGenerator.createType();
            nextMethod = type.methodSpecs.stream()
                    .filter(methodSpec -> methodSpec.name.equals(STATIC_BUILDER_METHOD_NAME))
                    .findFirst().orElseThrow(() -> new RuntimeException("static builder method not found"));
        }

        @Test
        void should_be_public_static_and_named_as_builder() {
            assertThat(nextMethod.modifiers).containsExactly(Modifier.PUBLIC, Modifier.STATIC);
            assertThat(nextMethod.name).isEqualTo(STATIC_BUILDER_METHOD_NAME);
        }

        @Test
        void should_return_given_type_and_take_no_parameter() {
            assertThat(nextMethod.returnType).hasToString(BUILDER_CLASS_NAME);
            assertThat(nextMethod.parameters).isEmpty();
        }

        @Test
        void should_invoke_constructor_of_builder() {
            assertThat(nextMethod.code).hasToString("return new SomeClassModeler().$builder();\n");
        }
    }

    @Nested
    class MemberNextMethodTest {

        private MethodSpec nextMethod;

        @BeforeEach
        void setUp() {
            final TypeSpec type = modelerGenerator.createType();
            nextMethod = type.methodSpecs.stream()
                    .filter(methodSpec -> methodSpec.name.equals(MEMBER_NEXT_METHOD_NAME))
                    .findFirst().orElseThrow(() -> new RuntimeException("member next method not found"));
        }

        @Test
        void should_be_public_static_and_named_as_next() {
            assertThat(nextMethod.modifiers).containsExactly(Modifier.PROTECTED);
            assertThat(nextMethod.name).isEqualTo(MEMBER_NEXT_METHOD_NAME);
        }

        @Test
        void should_return_given_type_and_take_no_parameter() {
            assertThat(nextMethod.returnType).isEqualTo(ClassName.get(SomeClass.class));
            assertThat(nextMethod.parameters).isEmpty();
        }

        @Test
        void should_invoke_builder_build() {
            assertThat(nextMethod.code).hasToString("return " + MEMBER_BUILDER_METHOD_NAME + "().build();\n");
        }
    }

    @Nested
    class MemberBuilderMethodTest {

        private MethodSpec nextMethod;

        @BeforeEach
        void setUp() {
            final TypeSpec type = modelerGenerator.createType();
            nextMethod = type.methodSpecs.stream()
                    .filter(methodSpec -> methodSpec.name.equals(MEMBER_BUILDER_METHOD_NAME))
                    .findFirst().orElseThrow(() -> new RuntimeException("member builder method not found"));
        }

        @Test
        void should_be_public_and_named_as_builder() {
            assertThat(nextMethod.modifiers).containsExactly(Modifier.PROTECTED);
            assertThat(nextMethod.name).isEqualTo(MEMBER_BUILDER_METHOD_NAME);
        }

        @Test
        void should_return_given_type_and_take_no_parameter() {
            assertThat(nextMethod.returnType).hasToString(BUILDER_CLASS_NAME);
            assertThat(nextMethod.parameters).isEmpty();
        }

        @Test
        void should_invoke_constructor_of_builder() {
            assertThat(nextMethod.code).hasToString("return new Builder();\n");
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
