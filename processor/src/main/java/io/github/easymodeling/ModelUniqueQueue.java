package io.github.easymodeling;

import io.github.easymodeling.processor.AnnoModelWrapper;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class ModelUniqueQueue {

    /**
     * The singleton of <code>ModelUniqueQueue</code>
     */
    private static ModelUniqueQueue instance;

    /**
     * The queue of all classes to be processed
     */
    private final Queue<AnnoModelWrapper> modelToBeProcessed;

    /**
     * The collection of classes that already processed and to be processed
     */
    private final Set<AnnoModelWrapper> modelAdded;

    private ModelUniqueQueue() {
        this.modelToBeProcessed = new LinkedBlockingQueue<>();
        this.modelAdded = new CopyOnWriteArraySet<>();
    }

    /**
     * Get the singleton of <code>ModelUniqueQueue</code>, should create
     * one if the singleton has not been created yet.
     *
     * @return the instance of <code>ModelUniqueQueue</code>
     */
    public static ModelUniqueQueue instance() {
        if (instance == null) {
            instance = new ModelUniqueQueue();
        }
        return instance;
    }

    /**
     * Retrieve and remove the head of the queue.
     *
     * @return the head of the queue, <code>null</code> if the queue is empty.
     */
    public AnnoModelWrapper poll() {
        return modelToBeProcessed.poll();
    }

    /**
     * Insert a class to the end of the queue if it is not registered yet.
     * Will do nothing if the class is already registered.
     *
     * @param typeCanonicalName the name of class to be added
     * @see #add(AnnoModelWrapper)
     */
    public void add(String typeCanonicalName) {
        add(new AnnoModelWrapper(typeCanonicalName));
    }

    /**
     * Insert a class to the end of the queue if it is not registered yet.
     * Will do nothing if the class is already registered.
     * <p>This will not only register a class with its name, but also the
     * instance of <code>Model</code> associated with it, which contains
     * the customization of the modeler.
     *
     * @param annoModelWrapper a wrapper of <code>Model</code>
     * @see #add(String)
     */
    public void add(AnnoModelWrapper annoModelWrapper) {
        if (modelAdded.add(annoModelWrapper)) {
            modelToBeProcessed.add(annoModelWrapper);
        }
    }
}
