package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class ByteRandomizer extends NumberRandomizer<Byte> {

    @Override
    public Byte next(long min, long max) {
        return doubleBetween(min, max).byteValue();
    }
}
