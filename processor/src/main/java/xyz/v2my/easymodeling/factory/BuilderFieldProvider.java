package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
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

public class BuilderFieldProvider {

    public ModelField provide(TypeName type, FieldWrapper field) {
        if (type.equals(ClassName.get(Byte.class)) || type.equals(ClassName.BYTE)) {
            return new ByteField(type, field);
        }
        if (type.equals(ClassName.get(Short.class)) || type.equals(ClassName.SHORT)) {
            return new ShortField(type, field);
        }
        if (type.equals(ClassName.get(Integer.class)) || type.equals(ClassName.INT)) {
            return new IntegerField(type, field);
        }
        if (type.equals(ClassName.get(Long.class)) || type.equals(ClassName.LONG)) {
            return new LongField(type, field);
        }
        if (type.equals(ClassName.get(Float.class)) || type.equals(ClassName.FLOAT)) {
            return new FloatField(type, field);
        }
        if (type.equals(ClassName.get(Double.class)) || type.equals(ClassName.DOUBLE)) {
            return new DoubleField(type, field);
        }
        if (type.equals(ClassName.get(Boolean.class)) || type.equals(ClassName.BOOLEAN)) {
            return new BooleanField(type, field);
        }
        if (type.equals(ClassName.get(Character.class)) || type.equals(ClassName.CHAR)) {
            return new CharField(type, field);
        }
        if (type.equals(ClassName.get(String.class))) {
            return new StringField(type, field);
        }
        return new GenericField(type, field);
    }
}
