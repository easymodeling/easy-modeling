package xyz.v2my.easymodeling.randomizer;

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
        return createModel(modelCache);
    }

    private T createModel(ModelCache modelCache) {
        final Class<T> clazz = type();
        final T model = createModelOf(clazz);
        modelCache.push(model);
        populate(model, modelCache);
        modelCache.pop(clazz);
        return model;
    }

    protected abstract void populate(T model, ModelCache modelCache);

    protected abstract Class<T> type();
}
