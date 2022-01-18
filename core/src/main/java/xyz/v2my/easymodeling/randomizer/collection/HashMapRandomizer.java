package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.HashMap;
import java.util.function.Supplier;

public class HashMapRandomizer<K, V> extends AbstractMapRandomizer<HashMap<K, V>, K, V> {

    public HashMapRandomizer(Randomizer<K> keyRandomizer, Randomizer<V> valueRandomizer, int minSize, int maxSize) {
        super(keyRandomizer, valueRandomizer, minSize, maxSize);
    }

    @Override
    protected Supplier<HashMap<K, V>> collectionFactory() {
        return HashMap::new;
    }
}
