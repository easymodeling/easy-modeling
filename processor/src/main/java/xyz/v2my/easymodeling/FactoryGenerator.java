package xyz.v2my.easymodeling;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class FactoryGenerator extends Generator {

    public FactoryGenerator(Elements elementUtils) {
        super(elementUtils);
    }

    @Override
    public void generate(TypeElement clazz, TypeSpec.Builder factoryBuilder) {
        final String builderTypeName = String.format("EM%sBuilder", clazz.getSimpleName());
        final MethodSpec builder = MethodSpec.methodBuilder("builder")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get("", builderTypeName))
                .addStatement("return new $N()", builderTypeName)
                .build();
        factoryBuilder.addMethod(builder);
    }
}
