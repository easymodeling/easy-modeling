package io.github.easymodeling;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import io.github.easymodeling.modeler.ModelWrapper;
import io.github.easymodeling.modeler.ModelerGenerator;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import static io.github.easymodeling.log.ProcessorLogger.log;
import static io.github.easymodeling.modeler.ModelFieldRegistry.basicTypeContains;

@AutoService(Processor.class)
@SupportedOptions({
        ModelsProcessor.LOG_MODEL,
})
public class ModelsProcessor extends AbstractProcessor {

    public static final String LOG_MODEL = "easymodeling.log.model";

    private Elements elementUtils;

    private Filer filer;

    private ModelUniqueQueue modelUniqueQueue;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        log.setMessager(processingEnv.getMessager(), processingEnv.getOptions().get(LOG_MODEL));
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        modelUniqueQueue = ModelUniqueQueue.instance();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }
        try {
            collectAnnotatedModels(roundEnv);
            processModels();
            return true;
        } catch (ProcessingException e) {
            // TODO: 19.12.21 move Diagnostic.Kind to exception
            log.error(e.getMessage());
            return false;
        }
    }

    private void collectAnnotatedModels(RoundEnvironment roundEnv) throws ProcessingException {
        final Set<? extends Element> elementsAnnotatedWithModels = roundEnv.getElementsAnnotatedWith(Models.class);
        final Set<? extends Element> elementsAnnotatedWithModel = roundEnv.getElementsAnnotatedWith(Model.class);
        Stream.concat(
                        elementsAnnotatedWithModels.stream()
                                .map(models -> models.getAnnotation(Models.class))
                                .map(Models::value)
                                .flatMap(Arrays::stream),
                        elementsAnnotatedWithModel.stream()
                                .map(model -> model.getAnnotation(Model.class))
                )
                .forEach(this::collectModel);
    }

    private void collectModel(Model model) {
        try {
            final String canonicalName = classNameOf(model);
            if (basicTypeContains(canonicalName)) {
                throw new BasicTypeModelerException(canonicalName);
            }
            final NamedModel namedModel = new NamedModel(canonicalName, model);
            modelUniqueQueue.add(namedModel);
        } catch (BasicTypeModelerException e) {
            log.warning(e.getMessage());
        }
    }

    private void processModels() {
        while (true) {
            NamedModel namedModel = modelUniqueQueue.poll();
            if (null == namedModel) {
                break;
            }
            TypeElement type = getTypeElementOf(namedModel.getCanonicalName());
            final ModelWrapper modelWrapper = new ModelWrapper(namedModel.getModel(), type);
            processModel(modelWrapper);
        }
    }

    private void processModel(ModelWrapper modelWrapper) throws ProcessingException {
        final ModelerGenerator modelFactory = new ModelerGenerator(modelWrapper);
        final TypeSpec factory = modelFactory.createType();
        try {
            final String pkg = modelWrapper.getModelPackage();
            JavaFile.builder(pkg, factory).build()
                    .writeTo(filer);
        } catch (IOException e) {
            // TODO: 19.12.21 throw exceptions with elaborate messages
            throw new ProcessingException("Error when generate factory");
        }
    }

    private String classNameOf(Model model) {
        try {
            return model.type().getCanonicalName();
        } catch (MirroredTypeException mte) {
            return classNameOf(mte);
        }
    }

    private String classNameOf(MirroredTypeException mte) {
        final TypeMirror typeMirror = mte.getTypeMirror();
        if (!(typeMirror instanceof DeclaredType)) {
            throw new BasicTypeModelerException(typeMirror.toString());
        }
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
        return Sets.newHashSet(Models.class.getCanonicalName(), Model.class.getCanonicalName());
    }

    private TypeElement getTypeElementOf(String canonicalName) {
        // TODO: 10.02.22 should avoid abstract classes and interfaces
        return elementUtils.getTypeElement(canonicalName);
    }
}
