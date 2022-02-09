package xyz.v2my.modeler;

import xyz.v2my.easymodeling.SomeNestedModel;
import xyz.v2my.easymodeling.randomizer.CustomTypeRandomizer;
import xyz.v2my.easymodeling.randomizer.ModelCache;
import xyz.v2my.easymodeling.randomizer.Modeler;
import xyz.v2my.easymodeling.randomizer.array.ArrayRandomizer;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;
import xyz.v2my.easymodeling.randomizer.string.StringRandomizer;

// TODO: 10.02.22 To be removed
public class SomeNestedModelModeler extends Modeler<SomeNestedModel> {

    public static SomeNestedModel next() {
        return new SomeNestedModelModeler().next(null);
    }

    @Override
    protected void populate(SomeNestedModel model, ModelCache modelCache) throws NoSuchFieldException, IllegalAccessException {
        setField(model, "string", new StringRandomizer(6, 20, 3).next());
        setField(model, "integer", new IntegerRandomizer(0.0, 2.147483647E9).next());
        setField(model, "nestedModel", new CustomTypeRandomizer<>(new SomeNestedModelModeler(), modelCache).next());
        setField(model, "arrayOfNestedModel", new ArrayRandomizer<>(new CustomTypeRandomizer<>(new SomeNestedModelModeler(), modelCache), 3, 10).next());
    }

    @Override
    protected Class<SomeNestedModel> type() {
        return SomeNestedModel.class;
    }
}
