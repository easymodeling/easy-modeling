package io.github.easymodeling;

import io.github.easymodeling.processor.ModelUniqueQueue;
import io.github.easymodeling.processor.NamedModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ModelUniqueQueueTest {

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        final Field repository = ModelUniqueQueue.class.getDeclaredField("instance");
        repository.setAccessible(true);
        repository.set(null, null);
    }

    @Test
    void should_get_singleton_with_instance_method() {
        ModelUniqueQueue modelUniqueQueue = ModelUniqueQueue.instance();
        ModelUniqueQueue modelUniqueQueue2 = ModelUniqueQueue.instance();

        assertThat(modelUniqueQueue).isEqualTo(modelUniqueQueue2).isNotNull();
    }

    @Test
    void should_add_element_to_the_queue_and_poll_saved_elements() {
        ModelUniqueQueue repository = ModelUniqueQueue.instance();
        final NamedModel string = new NamedModel(String.class.getCanonicalName());
        final NamedModel optional = new NamedModel(Optional.class.getCanonicalName());

        repository.add(string);
        repository.add(optional);

        assertThat(repository.poll()).isEqualTo(string);
        assertThat(repository.poll()).isEqualTo(optional);
        assertThat(repository.poll()).isNull();
    }

    @Test
    void should_avoid_duplicated_element_to_be_added() {
        ModelUniqueQueue repository = ModelUniqueQueue.instance();
        final NamedModel string = new NamedModel(String.class.getCanonicalName());
        final NamedModel optional = new NamedModel(Optional.class.getCanonicalName());
        final NamedModel anotherString = new NamedModel(Optional.class.getCanonicalName());

        repository.add(string);
        repository.add(optional);
        repository.add(anotherString);

        assertThat(repository.poll()).isEqualTo(string);
        assertThat(repository.poll()).isEqualTo(optional);
        assertThat(repository.poll()).isNull();
    }

    @Test
    void should_add_element_to_the_queue_with_canonical_names() {
        ModelUniqueQueue repository = ModelUniqueQueue.instance();

        repository.add(String.class.getCanonicalName());
        repository.add(Optional.class.getCanonicalName());

        assertThat(repository.poll()).isEqualTo(new NamedModel(String.class.getCanonicalName()));
        assertThat(repository.poll()).isEqualTo(new NamedModel(Optional.class.getCanonicalName()));
        assertThat(repository.poll()).isNull();
    }

    @Test
    void should_add_element_to_the_queue_with_canonical_names_and_void_duplication() {
        ModelUniqueQueue repository = ModelUniqueQueue.instance();

        repository.add(new NamedModel(String.class.getCanonicalName()));
        repository.add(Optional.class.getCanonicalName());
        repository.add(String.class.getCanonicalName());

        assertThat(repository.poll()).isEqualTo(new NamedModel(String.class.getCanonicalName()));
        assertThat(repository.poll()).isEqualTo(new NamedModel(Optional.class.getCanonicalName()));
        assertThat(repository.poll()).isNull();
    }
}
