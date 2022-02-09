package xyz.v2my.easymodeling.randomizer;

import java.lang.reflect.Field;

public abstract class Modeler<T> {

    protected void setField(T model, String fieldName, Object value) throws IllegalAccessException, NoSuchFieldException {
        final Field field = model.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(model, value);
    }

    protected T next(ModelCache modelCache) {
        if (modelCache == null) {
            modelCache = new ModelCache();
        }
        final Class<T> clazz = type();
        if (modelCache.avoidInfinity(clazz)) {
            return modelCache.first(clazz);
        }
        try {
            return createModel(modelCache);
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private T createModel(ModelCache modelCache) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        final Class<T> clazz = type();
        final T model = clazz.newInstance();
        modelCache.push(model);
        populate(model, modelCache);
        modelCache.pop(clazz);
        return model;
    }

    protected abstract void populate(T model, ModelCache modelCache) throws NoSuchFieldException, IllegalAccessException;

    protected abstract Class<T> type();
}
