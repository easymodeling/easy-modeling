package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.Randomizer;

import java.util.TreeMap;
import java.util.function.Supplier;

public class TreeMapRandomizer<K, V> extends AbstractMapRandomizer<TreeMap<K, V>, K, V> {

    public TreeMapRandomizer(Randomizer<K> keyRandomizer, Randomizer<V> valueRandomizer, int minSize, int maxSize) {
        super(keyRandomizer, valueRandomizer, minSize, maxSize);
    }

    @Override
    protected Supplier<TreeMap<K, V>> collectionFactory() {
        return TreeMap::new;
    }
}
