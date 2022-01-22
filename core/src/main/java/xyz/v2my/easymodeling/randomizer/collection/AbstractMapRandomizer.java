package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.Map;
import java.util.function.Supplier;

public abstract class AbstractMapRandomizer<M extends Map<K, V>, K, V> extends GenericRandomizer<M> {

    protected SetRandomizer<K> keyRandomizer;

    protected Randomizer<V> valueRandomizer;

    protected AbstractMapRandomizer(Randomizer<K> keyRandomizer, Randomizer<V> valueRandomizer, int minSize, int maxSize) {
        this.keyRandomizer = new SetRandomizer<>(keyRandomizer, minSize, maxSize);
        this.valueRandomizer = valueRandomizer;
    }

    @Override
    protected M random() {
        return keyRandomizer.next().stream()
                .collect(collectionFactory(), (m, k) -> m.put(k, valueRandomizer.next()), Map::putAll);
    }

    protected abstract Supplier<M> collectionFactory();
}
