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

    public static SomeNestedModelModeler.Builder builder() {
        return new SomeNestedModelModeler.Builder(next());
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

    public static class Builder extends BaseBuilder<SomeNestedModel> {

        private String string;

        private Integer integer;

        private SomeNestedModel nestedModel;

        private SomeNestedModel[] arrayOfNestedModel;

        private Builder(SomeNestedModel model) {
            this.string = (String) getField(model, "string");
            this.integer = (Integer) getField(model, "integer");
            this.nestedModel = (SomeNestedModel) getField(model, "nestedModel");
            this.arrayOfNestedModel = (SomeNestedModel[]) getField(model, "arrayOfNestedModel");
        }

        public SomeNestedModel build() {
            return new SomeNestedModel(string, integer, nestedModel, arrayOfNestedModel);
        }

        public SomeNestedModelModeler.Builder string(String string) {
            this.string = string;
            return this;
        }

        public SomeNestedModelModeler.Builder integer(Integer integer) {
            this.integer = integer;
            return this;
        }

        public SomeNestedModelModeler.Builder nestedModel(SomeNestedModel nestedModel) {
            this.nestedModel = nestedModel;
            return this;
        }

        public SomeNestedModelModeler.Builder arrayOfNestedModel(SomeNestedModel[] arrayOfNestedModel) {
            this.arrayOfNestedModel = arrayOfNestedModel;
            return this;
        }
    }
}
