package xyz.v2my.easymodeling;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.AllArgsConstructor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AutoService(Processor.class)
public class BuilderProcessor extends AbstractProcessor {

    private Messager messager;

    private Elements elementUtils;

    private Types typeUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }
        for (Element builderConfig : roundEnv.getElementsAnnotatedWith(Builder.class)) {
            final Builder builder = builderConfig.getAnnotation(Builder.class);

            final List<TypeElement> buildClasses = classOf(builder).stream().map(elementUtils::getTypeElement).collect(Collectors.toList());

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
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private List<String> classOf(Builder builder) {
        try {
            return Arrays.stream(builder.classes()).map(Class::getCanonicalName).collect(Collectors.toList());
        } catch (MirroredTypesException mte) {
            return mte.getTypeMirrors().stream().map(this::typeMirrorName).collect(Collectors.toList());
        }
    }

    private String typeMirrorName(TypeMirror typeMirror) {
        final DeclaredType declaredType = (DeclaredType) typeMirror;
        final TypeElement typeElement = (TypeElement) declaredType.asElement();
        return typeElement.getQualifiedName().toString();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Sets.newHashSet(Builder.class.getCanonicalName());
    }

}
