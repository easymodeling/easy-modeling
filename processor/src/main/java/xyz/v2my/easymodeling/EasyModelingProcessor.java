package xyz.v2my.easymodeling;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import lombok.AllArgsConstructor;
import xyz.v2my.easymodeling.field.BuilderField;

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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AutoService(Processor.class)
public class EasyModelingProcessor extends AbstractProcessor {

    private Elements elementUtils;

    private Filer filer;

    private Messager messager;

    private BuilderFieldProvider builderFieldProvider;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        builderFieldProvider = new BuilderFieldProvider();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }
        try {
            process(roundEnv);
        } catch (ProcessingException e) {
            // TODO: 19.12.21 move Diagnostic.Kind to exception
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            return false;
        }
        return false;
    }

    private void process(RoundEnvironment roundEnv) throws ProcessingException {
        for (Element element : roundEnv.getElementsAnnotatedWith(Builder.class)) {
            List<TypeElement> typeElements = classNamesOf(element).stream().map(elementUtils::getTypeElement).collect(Collectors.toList());
            process(typeElements);
        }
    }

    private void process(List<TypeElement> classes) throws ProcessingException {
        for (TypeElement clazz : classes) {
            if (clazz.getAnnotation(AllArgsConstructor.class) == null) {
                throw new RuntimeException();
            }

            final FactoryType modelFactory = new FactoryType(clazz, initBuilderFields(clazz));
            final TypeSpec factory = modelFactory.createType();
            try {
                final PackageElement pkg = elementUtils.getPackageOf(clazz);
                JavaFile.builder(pkg.toString(), factory)
                        .addStaticImport(ClassName.get("xyz.v2my.easymodeling", "Randomizer"), "*")
                        .build().writeTo(filer);
            } catch (IOException e) {
                // TODO: 19.12.21 throw exceptions with elaborate messages
                throw new ProcessingException("Error when generate factory");
            }
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Sets.newHashSet(Builder.class.getCanonicalName());
    }

    private List<BuilderField> initBuilderFields(TypeElement clazz) {
        return clazz.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .map(builderFieldProvider::provide)
                .collect(Collectors.toList());
    }

    private List<String> classNamesOf(Element easyModelingConfig) {
        final Builder builder = easyModelingConfig.getAnnotation(Builder.class);
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
}
