package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.factory.field.GenericField;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.NumericalField;
import xyz.v2my.easymodeling.factory.field.PrimitiveField;

import javax.lang.model.element.Element;

public class BuilderFieldProvider {

    public ModelField provide(Element element, Field field) {
        final TypeName type = ClassName.get(element.asType());
        final String name = element.getSimpleName().toString();
        if (type.equals(ClassName.get(Byte.class)) || type.equals(ClassName.BYTE)
                || type.equals(ClassName.get(Short.class)) || type.equals(ClassName.SHORT)
                || type.equals(ClassName.get(Integer.class)) || type.equals(ClassName.INT)
                || type.equals(ClassName.get(Long.class)) || type.equals(ClassName.LONG)
                || type.equals(ClassName.get(Float.class)) || type.equals(ClassName.FLOAT)
                || type.equals(ClassName.get(Double.class)) || type.equals(ClassName.DOUBLE)) {
            return new NumericalField(type, name, field);
        } else if (type.isPrimitive() || type.isBoxedPrimitive()) {
            return new PrimitiveField(type, name);
        }
        return new GenericField(type, name);
    }

}
