package xyz.v2my.easymodeling.randomizer;

public abstract class NumberRandomizer<T> extends GenericRandomizer<T> {

    public abstract T next(long min, long max);
}
