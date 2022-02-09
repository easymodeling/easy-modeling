package xyz.v2my.easymodeling.randomizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ModelCache {

    private final Map<Class<?>, Stack<?>> cache;

    public ModelCache() {
        this.cache = new HashMap<>();
    }

    public <T> void push(T obj) {
        final Class<?> clazz = obj.getClass();
        Stack<T> stack = (Stack<T>) cache.get(clazz);
        if (stack == null) {
            stack = new Stack<>();
            cache.put(clazz, stack);
        }
        stack.push(obj);
    }

    public <T> void pop(Class<T> clazz) {
        Stack<T> stack = (Stack<T>) cache.get(clazz);
        if (stack == null) {
            throw new IllegalStateException("No stack for class " + clazz);
        }
        stack.pop();
    }

    public boolean avoidInfinity(Class<?> clazz) {
        return cache.containsKey(clazz) && cache.get(clazz).size() > 4;
    }

    public <T> T first(Class<?> clazz) {
        Stack<T> stack = (Stack<T>) cache.get(clazz);
        if (stack == null) {
            throw new IllegalStateException("No stack for class " + clazz);
        }
        return stack.firstElement();
    }
}
