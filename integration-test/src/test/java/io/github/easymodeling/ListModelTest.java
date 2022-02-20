package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListModelTest {

    @Test
    void should_populate_list_model() {
        final ListModel model = ListModelModeler.next();

        assertThat(model).isNotNull();
        assertThat(model.listOfInts).isNotNull();
        assertThat(model.listOfPrimitiveInts).isNull();
        assertThat(model.arrayListOfListOfInts).isNotNull();
    }
}
