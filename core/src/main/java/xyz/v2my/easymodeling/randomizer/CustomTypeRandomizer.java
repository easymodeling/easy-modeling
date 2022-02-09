package xyz.v2my.easymodeling.randomizer;

public class CustomTypeRandomizer<T> extends GenericRandomizer<T> {

    private final Modeler<T> modeler;

    private final ModelCache modelCache;

    public CustomTypeRandomizer(Modeler<T> modeler, ModelCache modelCache) {
        this.modeler = modeler;
        this.modelCache = modelCache;
    }

    @Override
    protected T random() {
        return modeler.next(modelCache);
    }
}
