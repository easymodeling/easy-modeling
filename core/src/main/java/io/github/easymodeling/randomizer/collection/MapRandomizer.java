package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.Randomizer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MapRandomizer<K, V> extends AbstractMapRandomizer<Map<K, V>, K, V> {

    public MapRandomizer(Randomizer<K> keyRandomizer, Randomizer<V> valueRandomizer, int minSize, int maxSize) {
        super(keyRandomizer, valueRandomizer, minSize, maxSize);
    }

    @Override
    protected Supplier<Map<K, V>> collectionFactory() {
        return HashMap::new;
    }
}
