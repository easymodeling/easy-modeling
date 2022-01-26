package xyz.v2my.easymodeling;

import xyz.v2my.easymodeling.factory.ModelWrapper;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class ModelRepository {

    public static ModelRepository INSTANCE;

    private final Queue<ModelWrapper> modelToBeProcessed;

    private final Set<ModelWrapper> modelAdded;

    private ModelRepository(Queue<ModelWrapper> modelToBeProcessed, Set<ModelWrapper> modelAdded) {
        this.modelToBeProcessed = modelToBeProcessed;
        this.modelAdded = modelAdded;
    }

    public static ModelRepository instance() {
        if (INSTANCE == null) {
            INSTANCE = new ModelRepository(new LinkedBlockingQueue<>(), new CopyOnWriteArraySet<>());
        }
        return INSTANCE;
    }

    public ModelWrapper next() {
        return modelToBeProcessed.poll();
    }

    public void add(ModelWrapper modelWrapper) {
        if (modelAdded.add(modelWrapper)) {
            modelToBeProcessed.add(modelWrapper);
        }
    }
}
