package io.github.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import io.github.easymodeling.modeler.field.StatementProvider;
import io.github.easymodeling.processor.GenerationPatterns;
import io.github.easymodeling.randomizer.ModelCache;
import io.github.easymodeling.randomizer.Modeler;

import javax.lang.model.element.Modifier;

public class ModelerGenerator {

    private final ModeledClass modeledClass;

    public ModelerGenerator(ModeledClass modeledClass) {
        this.modeledClass = modeledClass;
    }

    public TypeSpec createType() {
        final TypeSpec.Builder modeler = TypeSpec.classBuilder(modelerName())
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(ClassName.get(Modeler.class), modeledClass.getModelTypeName()));
        final TypeSpec builder = new BuilderGenerator(modeledClass.getFields(), modeledClass.getModelTypeName()).createBuilder();
        modeler.addType(builder);

        modeler.addMethod(staticNextMethod());
        modeler.addMethod(staticBuilderMethod());
        modeler.addMethod(populateMethod());
        modeler.addMethod(typeMethod());

        return modeler.build();
    }

    private MethodSpec staticNextMethod() {
        return MethodSpec.methodBuilder(GenerationPatterns.STATIC_NEXT_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(modeledClass.getModelTypeName())
                .addStatement("return new $N().$N(null)", modelerName(), GenerationPatterns.BASE_MODELER_NEXT_METHOD_NAME)
                .build();
    }

    private MethodSpec staticBuilderMethod() {
        return MethodSpec.methodBuilder(GenerationPatterns.STATIC_BUILDER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get("", GenerationPatterns.BUILDER_CLASS_NAME))
                .addStatement("return new $N.$N($N())", modelerName(), GenerationPatterns.BUILDER_CLASS_NAME, GenerationPatterns.STATIC_NEXT_METHOD_NAME)
                .build();
    }

    private MethodSpec typeMethod() {
        return MethodSpec.methodBuilder(GenerationPatterns.TYPE_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(ParameterizedTypeName.get(ClassName.get(Class.class), modeledClass.getModelTypeName()))
                .addStatement("return $T.class", modeledClass.getModelTypeName())
                .build();
    }

    private MethodSpec populateMethod() {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(GenerationPatterns.MEMBER_POPULATE_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(modeledClass.getModelTypeName(), GenerationPatterns.MODEL_PARAMETER_NAME).build())
                .addParameter(ParameterSpec.builder(ModelCache.class, GenerationPatterns.MODEL_CACHE_PARAMETER_NAME).build());
        modeledClass.getFields().stream().map(StatementProvider::populateStatement)
                .forEach(builder::addStatement);
        return builder.build();
    }

    private String modelerName() {
        return String.format(GenerationPatterns.MODELER_NAME_PATTERN, modeledClass.getModelTypeName().simpleName());
    }
}
