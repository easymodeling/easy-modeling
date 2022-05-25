package io.github.easymodeling.modeler;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import io.github.easymodeling.randomizer.ModelCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ModelerGeneratorTest {

    private ModelerGenerator modelerGenerator;

    @BeforeEach
    void setUp() {
        modelerGenerator = new ModelerGenerator(ClassName.get(SomeClass.class), new ArrayList<>());
    }

    @Test
    void should_create_modeler_with_given_class_name() {
        final TypeSpec type = modelerGenerator.createType();

        assertThat(type.name).isEqualTo(SomeClass.class.getSimpleName() + "Modeler");
    }

    @Test
    void should_generate_modeler_methods() {
        final TypeSpec type = modelerGenerator.createType();

        assertThat(type.methodSpecs).hasSize(7);
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
    class StaticStreamMethodTest {

        private MethodSpec streamMethod;

        @BeforeEach
        void setUp() {
            final TypeSpec type = modelerGenerator.createType();
            streamMethod = type.methodSpecs.stream()
                    .filter(methodSpec -> methodSpec.name.equals(GenerationPatterns.STATIC_STREAM_METHOD_NAME))
                    .findFirst().orElseThrow(() -> new RuntimeException("static stream method not found"));
        }

        @Test
        void should_be_public_static_and_named_as_stream() {
            assertThat(streamMethod.modifiers).containsExactly(Modifier.PUBLIC, Modifier.STATIC);
            assertThat(streamMethod.name).isEqualTo(GenerationPatterns.STATIC_STREAM_METHOD_NAME);
        }

        @Test
        void should_return_stream_of_given_type_and_take_no_parameter() {
            assertThat(streamMethod.returnType).isEqualTo(ParameterizedTypeName.get(ClassName.get(Stream.class), ClassName.get(SomeClass.class)));
            assertThat(streamMethod.parameters).isEmpty();
        }

        @Test
        void should_generate_stream_from_static_next_method() {
            assertThat(streamMethod.code).hasToString("return " + Stream.class.getCanonicalName() + ".generate(() -> next());\n");
        }
    }

    @Nested
    class StaticSizedListMethodTest {

        private MethodSpec streamMethod;

        @BeforeEach
        void setUp() {
            final TypeSpec type = modelerGenerator.createType();
            streamMethod = type.methodSpecs.stream()
                    .filter(methodSpec -> methodSpec.name.equals(GenerationPatterns.STATIC_LIST_METHOD_NAME))
                    .filter(methodSpec -> methodSpec.parameters.size() == 1)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("static sized list method not found"));
        }

        @Test
        void should_be_public_static_and_named_as_list() {
            assertThat(streamMethod.modifiers).containsExactly(Modifier.PUBLIC, Modifier.STATIC);
            assertThat(streamMethod.name).isEqualTo(GenerationPatterns.STATIC_LIST_METHOD_NAME);
        }

        @Test
        void should_return_list_of_given_type_and_take_size_as_parameter() {
            assertThat(streamMethod.returnType)
                    .isEqualTo(ParameterizedTypeName.get(ClassName.get(List.class), ClassName.get(SomeClass.class)));
            assertThat(streamMethod.parameters)
                    .hasSize(1).containsOnly(ParameterSpec.builder(int.class, "size").build());
        }

        @Test
        void should_generate_list_collected_from_stream_method() {
            assertThat(streamMethod.code).hasToString("" +
                    "return stream()" +
                    ".limit(size)" +
                    ".collect(" + Collectors.class.getCanonicalName() + ".toList());\n");
        }
    }

    @Nested
    class StaticListMethodTest {

        private MethodSpec streamMethod;

        @BeforeEach
        void setUp() {
            final TypeSpec type = modelerGenerator.createType();
            streamMethod = type.methodSpecs.stream()
                    .filter(methodSpec -> methodSpec.name.equals(GenerationPatterns.STATIC_LIST_METHOD_NAME))
                    .filter(methodSpec -> methodSpec.parameters.isEmpty())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("static list method not found"));
        }

        @Test
        void should_be_public_static_and_named_as_list() {
            assertThat(streamMethod.modifiers).containsExactly(Modifier.PUBLIC, Modifier.STATIC);
            assertThat(streamMethod.name).isEqualTo(GenerationPatterns.STATIC_LIST_METHOD_NAME);
        }

        @Test
        void should_return_list_of_given_type_and_take_no_parameter() {
            assertThat(streamMethod.returnType)
                    .isEqualTo(ParameterizedTypeName.get(ClassName.get(List.class), ClassName.get(SomeClass.class)));
            assertThat(streamMethod.parameters).hasSize(0);
        }

        @Test
        void should_generate_list_from_sized_list_method() {
            assertThat(streamMethod.code).hasToString("return list(new " + Random.class.getCanonicalName() + "().nextInt(7) + 1);\n");
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
