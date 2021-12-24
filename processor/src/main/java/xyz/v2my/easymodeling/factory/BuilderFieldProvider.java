package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.field.BoxedPrimitiveBuilderField;
import xyz.v2my.easymodeling.factory.field.BuilderField;
import xyz.v2my.easymodeling.factory.field.GenericBuilderField;
import xyz.v2my.easymodeling.factory.field.PrimitiveBuilderField;

import javax.lang.model.element.Element;

public class BuilderFieldProvider {

    public BuilderField provide(Element element) {
        final TypeName type = ClassName.get(element.asType());
        final String name = element.getSimpleName().toString();
        if (type.isPrimitive()) {
            return new PrimitiveBuilderField(type, name);
        } else if (type.isBoxedPrimitive()) {
            return new BoxedPrimitiveBuilderField(type, name);
        }
        return new GenericBuilderField(type, name);
    }

}
