package xyz.v2my.easymodeling;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class ModelUniqueQueue {

    public static ModelUniqueQueue INSTANCE;

    private final Queue<NamedModel> modelToBeProcessed;

    private final Set<NamedModel> modelAdded;

    private ModelUniqueQueue(Queue<NamedModel> modelToBeProcessed, Set<NamedModel> modelAdded) {
        this.modelToBeProcessed = modelToBeProcessed;
        this.modelAdded = modelAdded;
    }

    public static ModelUniqueQueue instance() {
        if (INSTANCE == null) {
            INSTANCE = new ModelUniqueQueue(new LinkedBlockingQueue<>(), new CopyOnWriteArraySet<>());
        }
        return INSTANCE;
    }

    public NamedModel poll() {
        return modelToBeProcessed.poll();
    }

    public void add(NamedModel namedModel) {
        if (modelAdded.add(namedModel)) {
            modelToBeProcessed.add(namedModel);
        }
    }
}
