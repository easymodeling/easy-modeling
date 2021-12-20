package xyz.v2my.easymodeling;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FactoryGenerator extends Generator {

    private final Elements elementUtils;

    private final Filer filer;

    public FactoryGenerator(Elements elementUtils, Filer filer) {
        this.elementUtils = elementUtils;
        this.filer = filer;
    }

    @Override
    public void generate(Element easyModelingConfig) throws ProcessingException {
        final List<TypeElement> buildClasses = classOf(easyModelingConfig).stream().map(elementUtils::getTypeElement).collect(Collectors.toList());

        for (TypeElement buildClass : buildClasses) {
            final String factoryTypeName = String.format("EM%sFactory", buildClass.getSimpleName());
            final TypeSpec.Builder factoryBuilder = TypeSpec.classBuilder(factoryTypeName).addModifiers(Modifier.PUBLIC);
            final String builderTypeName = String.format(BUILDER_TYPE_NAME_TEMPLATE, buildClass.getSimpleName());
            final PackageElement pkg = elementUtils.getPackageOf(buildClass);
            final MethodSpec builder = MethodSpec.methodBuilder("builder")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(ClassName.get(pkg.toString(), builderTypeName))
                    .addStatement("return new $N()", builderTypeName)
                    .build();
            factoryBuilder.addMethod(builder);
            try {
                JavaFile.builder(pkg.toString(), factoryBuilder.build()).build().writeTo(filer);
            } catch (IOException e) {
                // TODO: 19.12.21 throw exceptions with elaborate messages
                throw new ProcessingException("Error when generate factory");
            }
        }
    }
}
