package xyz.v2my.easymodeling;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class ModelRepository {

    public static ModelRepository INSTANCE;

    private final Queue<NamedModel> modelToBeProcessed;

    private final Set<NamedModel> modelAdded;

    private ModelRepository(Queue<NamedModel> modelToBeProcessed, Set<NamedModel> modelAdded) {
        this.modelToBeProcessed = modelToBeProcessed;
        this.modelAdded = modelAdded;
    }

    public static ModelRepository instance() {
        if (INSTANCE == null) {
            INSTANCE = new ModelRepository(new LinkedBlockingQueue<>(), new CopyOnWriteArraySet<>());
        }
        return INSTANCE;
    }

    public NamedModel next() {
        return modelToBeProcessed.poll();
    }

    public void add(NamedModel namedModel) {
        if (modelAdded.add(namedModel)) {
            modelToBeProcessed.add(namedModel);
        }
    }
}
