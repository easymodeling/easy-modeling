package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.factory.field.GenericField;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.numeric.ByteField;
import xyz.v2my.easymodeling.factory.field.numeric.DoubleField;
import xyz.v2my.easymodeling.factory.field.numeric.FloatField;
import xyz.v2my.easymodeling.factory.field.numeric.IntegerField;
import xyz.v2my.easymodeling.factory.field.numeric.LongField;
import xyz.v2my.easymodeling.factory.field.numeric.ShortField;
import xyz.v2my.easymodeling.factory.field.primitive.BooleanField;
import xyz.v2my.easymodeling.factory.field.primitive.CharField;
import xyz.v2my.easymodeling.factory.field.string.StringField;

import javax.lang.model.element.Element;

public class BuilderFieldProvider {

    public ModelField provide(Element element, Field field) {
        final TypeName type = ClassName.get(element.asType());
        final String name = element.getSimpleName().toString();
        if (type.equals(ClassName.get(Byte.class)) || type.equals(ClassName.BYTE)) {
            return new ByteField(type, name, field);
        }
        if (type.equals(ClassName.get(Short.class)) || type.equals(ClassName.SHORT)) {
            return new ShortField(type, name, field);
        }
        if (type.equals(ClassName.get(Integer.class)) || type.equals(ClassName.INT)) {
            return new IntegerField(type, name, field);
        }
        if (type.equals(ClassName.get(Long.class)) || type.equals(ClassName.LONG)) {
            return new LongField(type, name, field);
        }
        if (type.equals(ClassName.get(Float.class)) || type.equals(ClassName.FLOAT)) {
            return new FloatField(type, name, field);
        }
        if (type.equals(ClassName.get(Double.class)) || type.equals(ClassName.DOUBLE)) {
            return new DoubleField(type, name, field);
        }
        if (type.equals(ClassName.get(Boolean.class)) || type.equals(ClassName.BOOLEAN)) {
            return new BooleanField(type, name);
        }
        if (type.equals(ClassName.get(Character.class)) || type.equals(ClassName.CHAR)) {
            return new CharField(type, name);
        }
        if (type.equals(ClassName.get(String.class))) {
            return new StringField(type, name, field);
        }
        return new GenericField(type, name);
    }
}
