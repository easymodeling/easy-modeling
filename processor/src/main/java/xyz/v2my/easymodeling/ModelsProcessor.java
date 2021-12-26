package xyz.v2my.easymodeling;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import lombok.AllArgsConstructor;
import xyz.v2my.easymodeling.factory.FactoryClass;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
public class ModelsProcessor extends AbstractProcessor {

    private Elements elementUtils;

    private Filer filer;

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
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
        for (Element modelsAnnotated : roundEnv.getElementsAnnotatedWith(Models.class)) {
            final Models models = modelsAnnotated.getAnnotation(Models.class);
            for (Model model : models.value()) {
                process(model);
            }
        }
    }

    private void process(Model model) throws ProcessingException {
        TypeElement type = getTypeElementOf(model);
        final FactoryClass modelFactory = new FactoryClass(model, type);
        final TypeSpec factory = modelFactory.createType();
        try {
            final PackageElement pkg = elementUtils.getPackageOf(type);
            final JavaFile.Builder javaFile = JavaFile.builder(pkg.toString(), factory);
            modelFactory.imports().forEach(ipt ->
                    javaFile.addStaticImport(ipt.getClazz(), ipt.getName()));
            javaFile.build()
                    .writeTo(filer);
        } catch (IOException e) {
            // TODO: 19.12.21 throw exceptions with elaborate messages
            throw new ProcessingException("Error when generate factory");
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Sets.newHashSet(Models.class.getCanonicalName());
    }

    private TypeElement getTypeElementOf(Model model) {
        String className = classNameOf(model);
        TypeElement type = elementUtils.getTypeElement(className);
        if (type.getAnnotation(AllArgsConstructor.class) == null) {
            // TODO: 25.12.21 decouple from lombok
            throw new RuntimeException();
        }
        return type;
    }

    private String classNameOf(Model model) {
        try {
            return model.type().getCanonicalName();
        } catch (MirroredTypeException mte) {
            final TypeMirror typeMirror = mte.getTypeMirror();
            final DeclaredType declaredType = (DeclaredType) typeMirror;
            final TypeElement typeElement = (TypeElement) declaredType.asElement();
            return typeElement.getQualifiedName().toString();
        }
    }
}
