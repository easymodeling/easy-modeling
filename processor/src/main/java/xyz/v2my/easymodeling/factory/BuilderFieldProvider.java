package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.factory.field.GenericField;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.PrimitiveField;

import javax.lang.model.element.Element;

public class BuilderFieldProvider {

    public ModelField provide(Field fieldDeclaration, Element element) {
        final TypeName type = ClassName.get(element.asType());
        final String name = element.getSimpleName().toString();
        if (type.isPrimitive() || type.isBoxedPrimitive()) {
            return new PrimitiveField(fieldDeclaration, type, name);
        }
        return new GenericField(type, name);
    }

}
