package xyz.v2my.easymodeling.randomizer;

public class SomeTypeModeler extends Modeler<SomeType> {

    public static SomeType next() {
        return new SomeTypeModeler().next(null);
    }

    @Override
    protected void populate(SomeType model, ModelCache modelCache) {
    }

    @Override
    protected Class<SomeType> type() {
        return SomeType.class;
    }
}
