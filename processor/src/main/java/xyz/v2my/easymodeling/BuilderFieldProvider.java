package xyz.v2my.easymodeling;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;

public class BuilderFieldProvider {

    public BuilderField provide(Element element) {
        final TypeName type = ClassName.get(element.asType());
        final String name = element.getSimpleName().toString();
        if (type.isPrimitive()) {
            return new PrimitiveBuilderField(type, name);
        }
        return new GenericBuilderField(type, name);
    }

}
