package xyz.v2my.easymodeling;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.AllArgsConstructor;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BuilderGenerator extends Generator {

    private final Elements elementUtils;

    private final Filer filer;

    public BuilderGenerator(Elements elementUtils, Filer filer) {
        this.elementUtils = elementUtils;
        this.filer = filer;
    }

    @Override
    public void generate(Element easyModelingConfig) throws ProcessingException {
        final List<TypeElement> buildClasses = classOf(easyModelingConfig).stream().map(elementUtils::getTypeElement).collect(Collectors.toList());

        for (TypeElement buildClass : buildClasses) {
            final String builderTypeName = String.format("EM%sBuilder", buildClass.getSimpleName());
            final TypeSpec.Builder builderBuilder = TypeSpec.classBuilder(builderTypeName).addModifiers(Modifier.PUBLIC);

            if (buildClass.getAnnotation(AllArgsConstructor.class) == null) {
                throw new RuntimeException();
            }
            final MethodSpec builderConstructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).build();
            builderBuilder.addMethod(builderConstructor);

            final String constructionParameters = buildClass.getEnclosedElements().stream()
                    .filter(element -> !(!element.getKind().equals(ElementKind.FIELD) || element.getModifiers().contains(Modifier.STATIC)))
                    .map(Element::getSimpleName)
                    .collect(Collectors.joining(", "));
            final MethodSpec buildMethod = MethodSpec.methodBuilder("build")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(ClassName.get(buildClass))
                    .addStatement("return new $N(" + constructionParameters + ")", buildClass.getSimpleName())
                    .build();
            builderBuilder.addMethod(buildMethod);

            final PackageElement pkg = elementUtils.getPackageOf(buildClass);
            for (Element enclosedElement : buildClass.getEnclosedElements()) {
                if (!enclosedElement.getKind().equals(ElementKind.FIELD) || enclosedElement.getModifiers().contains(Modifier.STATIC)) {
                    continue;
                }
                final VariableElement field = (VariableElement) enclosedElement;
                final String fieldName = field.getSimpleName().toString();
                final FieldSpec builderField = FieldSpec.builder(ClassName.get(field.asType()), fieldName, Modifier.PRIVATE).build();
                final MethodSpec builderSetter = MethodSpec.methodBuilder(fieldName)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(ClassName.get(pkg.toString(), builderTypeName))
                        .addParameter(ClassName.get(field.asType()), fieldName)
                        .addStatement("this.$N = $N", fieldName, fieldName)
                        .addStatement("return this")
                        .build();
                builderBuilder
                        .addField(builderField)
                        .addMethod(builderSetter);
            }
            try {
                JavaFile.builder(pkg.toString(), builderBuilder.build()).build().writeTo(filer);
            } catch (IOException e) {
                // TODO: 19.12.21 throw exceptions with elaborate messages
                throw new ProcessingException("Error when generate builder");
            }
        }
    }

}
