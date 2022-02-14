package io.github.easymodeling.randomizer;

public class EnumRandomizer<E extends Enum<E>> extends GenericRandomizer<E> {

    private final E[] values;

    public EnumRandomizer(E[] values) {
        this.values = values;
    }

    @Override
    protected E random() {
        final int index = random.nextInt(values.length);
        return values[index];
    }
}
