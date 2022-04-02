package io.github.easymodeling.randomizer;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ModelCache {

    public final static int POOL_SIZE = 10;

    private final Map<Class<?>, List<?>> cache;

    private final Random random = new SecureRandom();

    public ModelCache() {
        this.cache = new HashMap<>();
    }

    public <T> void push(T obj) {
        final Class<?> clazz = obj.getClass();
        List<T> pool = (List<T>) cache.computeIfAbsent(clazz, k -> new ArrayList<>());
        pool.add(obj);
    }

    public boolean avoidInfinity(Class<?> clazz) {
        return cache.containsKey(clazz) && cache.get(clazz).size() >= POOL_SIZE;
    }

    public <T> T random(Class<?> clazz) {
        final List<T> pool = this.poolOf(clazz);
        return pool.get(random.nextInt(pool.size()));
    }

    private <T> List<T> poolOf(Class<?> clazz) {
        List<T> pool = (List<T>) cache.get(clazz);
        if (pool == null) {
            throw new IllegalStateException("No pool for class " + clazz);
        }
        return pool;
    }
}
