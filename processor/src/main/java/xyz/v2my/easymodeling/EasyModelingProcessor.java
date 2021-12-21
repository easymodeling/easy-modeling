package xyz.v2my.easymodeling;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
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

    private BuilderGenerator builderGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();

        initGenerators(processingEnv);
    }

    private void initGenerators(ProcessingEnvironment processingEnv) {
        final Elements elementUtils = processingEnv.getElementUtils();
        builderGenerator = new BuilderGenerator(elementUtils);
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
        for (Element easyModelingConfig : roundEnv.getElementsAnnotatedWith(Builder.class)) {
            final List<TypeElement> classes = classOf(easyModelingConfig).stream().map(elementUtils::getTypeElement).collect(Collectors.toList());
            for (TypeElement clazz : classes) {
                final String factoryTypeName = String.format("EM%sFactory", clazz.getSimpleName());
                final TypeSpec.Builder factoryBuilder = TypeSpec.classBuilder(factoryTypeName).addModifiers(Modifier.PUBLIC);

                final String builderTypeName = String.format("EM%sBuilder", clazz.getSimpleName());
                final MethodSpec builder = MethodSpec.methodBuilder("builder")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(ClassName.get("", builderTypeName))
                        .addStatement("return new $N()", builderTypeName)
                        .build();
                factoryBuilder.addMethod(builder);

                builderGenerator.generate(clazz, factoryBuilder);
                try {
                    final PackageElement pkg = elementUtils.getPackageOf(clazz);
                    JavaFile.builder(pkg.toString(), factoryBuilder.build()).build().writeTo(filer);
                } catch (IOException e) {
                    // TODO: 19.12.21 throw exceptions with elaborate messages
                    throw new ProcessingException("Error when generate factory");
                }
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

    private List<String> classOf(Element easyModelingConfig) {
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
