package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.Randomizer;

import java.util.HashMap;
import java.util.function.Supplier;

public class HashMapRandomizer<K, V> extends AbstractMapRandomizer<HashMap<K, V>, K, V> {

    public HashMapRandomizer(Randomizer<K> keyRandomizer, Randomizer<V> valueRandomizer, int maxSize) {
        super(keyRandomizer, valueRandomizer, maxSize);
    }

    @Override
    protected Supplier<HashMap<K, V>> collectionFactory() {
        return HashMap::new;
    }
}
