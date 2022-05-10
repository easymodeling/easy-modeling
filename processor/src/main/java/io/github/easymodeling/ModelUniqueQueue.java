package io.github.easymodeling;

import io.github.easymodeling.processor.AnnoModelWrapper;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class ModelUniqueQueue {

    private static ModelUniqueQueue instance;

    private final Queue<AnnoModelWrapper> modelToBeProcessed;

    private final Set<AnnoModelWrapper> modelAdded;

    private ModelUniqueQueue(Queue<AnnoModelWrapper> modelToBeProcessed, Set<AnnoModelWrapper> modelAdded) {
        this.modelToBeProcessed = modelToBeProcessed;
        this.modelAdded = modelAdded;
    }

    public static ModelUniqueQueue instance() {
        if (instance == null) {
            instance = new ModelUniqueQueue(new LinkedBlockingQueue<>(), new CopyOnWriteArraySet<>());
        }
        return instance;
    }

    public AnnoModelWrapper poll() {
        return modelToBeProcessed.poll();
    }

    public void add(String typeCanonicalName) {
        add(new AnnoModelWrapper(typeCanonicalName));
    }

    public void add(AnnoModelWrapper annoModelWrapper) {
        if (modelAdded.add(annoModelWrapper)) {
            modelToBeProcessed.add(annoModelWrapper);
        }
    }
}
