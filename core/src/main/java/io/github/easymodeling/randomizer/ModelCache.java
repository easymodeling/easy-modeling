package io.github.easymodeling.randomizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class ModelCache {

    public final static int STACK_SIZE = 10;

    private final Map<Class<?>, Stack<?>> cache;

    private final Random random = new Random();

    public ModelCache() {
        this.cache = new HashMap<>();
    }

    public <T> void push(T obj) {
        final Class<?> clazz = obj.getClass();
        Stack<T> stack = (Stack<T>) cache.computeIfAbsent(clazz, k -> new Stack<>());
        stack.push(obj);
    }

    public boolean avoidInfinity(Class<?> clazz) {
        return cache.containsKey(clazz) && cache.get(clazz).size() >= STACK_SIZE;
    }

    public <T> T first(Class<?> clazz) {
        return this.<T>stackOf(clazz).firstElement();
    }

    public <T> T random(Class<?> clazz) {
        return this.<T>stackOf(clazz).get(random.nextInt(this.<T>stackOf(clazz).size()));
    }

    private <T> Stack<T> stackOf(Class<?> clazz) {
        Stack<T> stack = (Stack<T>) cache.get(clazz);
        if (stack == null) {
            throw new IllegalStateException("No stack for class " + clazz);
        }
        return stack;
    }
}
