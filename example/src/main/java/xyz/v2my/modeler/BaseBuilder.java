package xyz.v2my.modeler;

import java.lang.reflect.Field;

public class BaseBuilder<T> {

    protected Object getField(T model, String fieldName) {
        try {
            final Field field = model.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(model);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
