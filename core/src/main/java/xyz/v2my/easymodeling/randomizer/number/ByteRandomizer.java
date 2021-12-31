package xyz.v2my.easymodeling.randomizer.number;


public class ByteRandomizer extends NumberRandomizer<Byte> {

    public ByteRandomizer(double min, double max) {
        super(min, max);
    }

    public ByteRandomizer(Byte constant) {
        super(constant);
    }

    @Override
    public Byte random() {
        return doubleBetween(this.min, this.max).byteValue();
    }
}
