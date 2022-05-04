package io.github.easymodeling.processor;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class ModelUniqueQueue {

    private static ModelUniqueQueue instance;

    private final Queue<NamedModel> modelToBeProcessed;

    private final Set<NamedModel> modelAdded;

    private ModelUniqueQueue(Queue<NamedModel> modelToBeProcessed, Set<NamedModel> modelAdded) {
        this.modelToBeProcessed = modelToBeProcessed;
        this.modelAdded = modelAdded;
    }

    public static ModelUniqueQueue instance() {
        if (instance == null) {
            instance = new ModelUniqueQueue(new LinkedBlockingQueue<>(), new CopyOnWriteArraySet<>());
        }
        return instance;
    }

    public NamedModel poll() {
        return modelToBeProcessed.poll();
    }

    public void add(String typeCanonicalName) {
        add(new NamedModel(typeCanonicalName));
    }

    public void add(NamedModel namedModel) {
        if (modelAdded.add(namedModel)) {
            modelToBeProcessed.add(namedModel);
        }
    }
}
