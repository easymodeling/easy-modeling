package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class ByteRandomizer extends NumberRandomizer<Byte> {

    public ByteRandomizer(double min, double max) {
        super(min, max);
    }

    @Override
    public Byte next() {
        return doubleBetween(this.min, this.max).byteValue();
    }
}
