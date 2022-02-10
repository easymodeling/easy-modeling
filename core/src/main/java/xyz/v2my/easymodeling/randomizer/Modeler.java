package xyz.v2my.easymodeling.randomizer;

import java.lang.reflect.InvocationTargetException;

import static xyz.v2my.easymodeling.ReflectionUtil.createModelOf;

public abstract class Modeler<T> {

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
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private T createModel(ModelCache modelCache) throws InstantiationException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        final Class<T> clazz = type();
        final T model = createModelOf(clazz);
        modelCache.push(model);
        populate(model, modelCache);
        modelCache.pop(clazz);
        return model;
    }

    protected abstract void populate(T model, ModelCache modelCache) throws NoSuchFieldException, IllegalAccessException;

    protected abstract Class<T> type();
}
