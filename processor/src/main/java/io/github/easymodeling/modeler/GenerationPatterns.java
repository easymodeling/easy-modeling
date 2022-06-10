package io.github.easymodeling.modeler;

import com.squareup.javapoet.ClassName;

import java.time.ZonedDateTime;
import java.util.function.Function;
import java.util.function.Supplier;

public class GenerationPatterns {

    private GenerationPatterns() {
    }

    public static final String BUILDER_CLASS_NAME = "Builder";

    public static final Function<ClassName, String> BUILDER_CLASS_JAVADOC = className -> "" +
            "An implementation of the builder pattern for \n" +
            "{@link " + classReference(className) + "} \n" +
            "\n" +
            "<p>" +
            "Fields declared in {@link " + classReference(className) + "} \n" +
            "will all be included in the builder, including some fields with \n" +
            "their type that currently not be pre-populated. \n" +
            "<p>" +
            "Fields that inherited from super classes will be included \n" +
            "as well, except some of them may be hidden by fields with the same \n" +
            "name in a subclass, which means they cannot be specified with the \n" +
            "builder. However, these hidden fields will also been randomly \n" +
            "populated so that they would generally not be null.\n";

    public static final String STATIC_BUILDER_METHOD_NAME = "builder";

    public static final Function<ClassName, String> STATIC_BUILDER_METHOD_JAVADOC = className -> "" +
            "Returns an instance of randomly populated builder of type \n" +
            "{@link " + BUILDER_CLASS_NAME + " " + BUILDER_CLASS_NAME + "}. \n" +
            "\n" +
            "<p>" +
            "Like general builder patterns, it is possible to customize \n" +
            "each fields of the builder via the method with the same name \n" +
            "of the fields, so it will be reflected to the instance of \n" +
            "{@link " + classReference(className) + " \n" +
            "generated from an invocation to {@link " + BUILDER_CLASS_NAME + "#build() build()} \n" +
            "of the customized builder. \n" +
            "<p>\n" +
            "In addition, The builder has already been populated randomly. \n" +
            "\n" +
            "@return an instance of randomly populated " +
            "{@link " + BUILDER_CLASS_NAME + " " + STATIC_BUILDER_METHOD_NAME + "} \n" +
            "\n" +
            "@see " + BUILDER_CLASS_NAME;

    public static final String STATIC_LIST_METHOD_NAME = "list";

    public static final Function<ClassName, String> STATIC_LIST_METHOD_JAVADOC = className -> "" +
            "Returns a {@code List} of randomly populated instance of \n" +
            "{@link " + classReference(className) + "} \n" +
            "\n" +
            "<p>" +
            "The size of generated list is also randomly specified, ranged from \n" +
            "{@code 1} (inclusive) to {@code 7} (inclusive). Use " +
            "{@link #" + STATIC_LIST_METHOD_NAME + "(int)} \n" +
            "to specify the size manually.\n" +
            "\n" +
            "@return a {@code List} of randomly populated \n" +
            "        {@link " + classReference(className) + "} \n" +
            "\n" +
            "@see #" + STATIC_LIST_METHOD_NAME + "(int)";

    public static final Function<ClassName, String> STATIC_SIZED_LIST_METHOD_JAVADOC = className -> "" +
            "Returns a {@code List} of randomly populated instance of \n" +
            "{@link " + classReference(className) + "} with given size. \n" +
            "\n" +
            "@param size the size of generated list \n" +
            "@return a {@code List} of randomly populated \n" +
            "        {@link " + classReference(className) + "} \n" +
            "\n" +
            "@see #" + STATIC_LIST_METHOD_NAME + "(int)";

    public static final String STATIC_NEXT_METHOD_NAME = "next";

    public static final Function<ClassName, String> STATIC_NEXT_METHOD_JAVADOC = className -> "" +
            "Returns a randomly populated instance. \n" +
            "\n" +
            "@return a randomly populated instance of \n" +
            "        {@link " + classReference(className) + "} \n";

    public static final String STATIC_STREAM_METHOD_NAME = "stream";

    public static final Function<ClassName, String> STATIC_STREAM_METHOD_JAVADOC = className -> "" +
            "Returns a {@link java.util.stream.Stream stream} of randomly \n" +
            "populated instance of {@link " + classReference(className) + "} \n" +
            "\n" +
            "<p>" +
            "The generated stream is infinite, so it is crucial to use a \n" +
            "{@code limit()} method before executing any terminal operation \n" +
            "of the stream. \n" +
            "\n" +
            "@return a {@link java.util.stream.Stream stream} of randomly populated \n" +
            "        {@link " + classReference(className) + "} \n";

    public static final String BASE_MODELER_NEXT_METHOD_NAME = "next";

    public static final String TYPE_METHOD_NAME = "type";

    public static final String MEMBER_POPULATE_METHOD_NAME = "populate";

    public static final String MODELER_NAME_PATTERN = "%sModeler";

    public static final Supplier<String> MODELER_JAVADOC = () ->
            "Generated by EasyModeling at " + ZonedDateTime.now() + "\n";

    public static final String MODEL_CACHE_PARAMETER_NAME = "modelCache";

    public static final String MODEL_PARAMETER_NAME = "model";

    private static String classReference(ClassName className) {
        return className.canonicalName() + " " + className.simpleName();
    }
}
