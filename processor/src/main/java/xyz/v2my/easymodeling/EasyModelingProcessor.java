package xyz.v2my.easymodeling;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
public class EasyModelingProcessor extends AbstractProcessor {

    private Generator builderProcessor;

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        builderProcessor = new BuilderGenerator(processingEnv.getElementUtils(), processingEnv.getFiler());
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
            builderProcessor.generate(easyModelingConfig);
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

}
