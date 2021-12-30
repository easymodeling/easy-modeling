package xyz.v2my.easymodeling.randomizer.number;


public class ByteRandomizer extends NumberRandomizer<Byte> {

    public ByteRandomizer(double min, double max) {
        super(min, max);
    }

    @Override
    public Byte next() {
        return doubleBetween(this.min, this.max).byteValue();
    }
}
