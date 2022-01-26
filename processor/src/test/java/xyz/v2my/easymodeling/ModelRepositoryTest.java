package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.ModelWrapper;
import xyz.v2my.easymodeling.factory.helper.ModelWrapperFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ModelRepositoryTest {

    @Test
    void should_get_singleton_with_instance_method() {
        ModelRepository modelRepository = ModelRepository.instance();
        ModelRepository modelRepository2 = ModelRepository.instance();

        assertThat(modelRepository).isEqualTo(modelRepository2).isNotNull();
    }

    @Test
    void should_add_element_to_the_queue_and_poll_saved_elements() {
        ModelRepository repository = ModelRepository.instance();
        final ModelWrapper string = ModelWrapperFactory.create(String.class).build();
        final ModelWrapper optional = ModelWrapperFactory.create(Optional.class).build();

        repository.add(string);
        repository.add(optional);

        assertThat(repository.next()).isEqualTo(string);
        assertThat(repository.next()).isEqualTo(optional);
        assertThat(repository.next()).isNull();
    }

    @Test
    void should_avoid_duplicated_element_to_be_added() {
        ModelRepository repository = ModelRepository.instance();
        final ModelWrapper string = ModelWrapperFactory.create(String.class).build();
        final ModelWrapper optional = ModelWrapperFactory.create(Optional.class).build();

        repository.add(string);
        repository.add(optional);
        repository.add(string);

        assertThat(repository.next()).isEqualTo(string);
        assertThat(repository.next()).isEqualTo(optional);
        assertThat(repository.next()).isNull();
    }
}
