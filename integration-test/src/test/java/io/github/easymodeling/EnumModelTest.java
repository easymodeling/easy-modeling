package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EnumModelTest {

    @Test
    void should_generate_enum_field() {
        final EnumModel next = EnumModelModeler.next();

        assertThat(next).isNotNull();
        assertThat(next.enumExample).isNotNull();
        assertThat(next.enumExamples).isNotNull();
        assertThat(next.listOfEnumExamples).isNotNull();
        assertThat(next.node).isNotNull();
    }

    @Test
    void should_provide_stream_of_models() {
        final Stream<EnumModel> models = EnumModelModeler.stream().limit(5);

        models.forEach(model -> {
            assertThat(model).isNotNull();
            assertThat(model.enumExample).isNotNull();
            assertThat(model.enumExamples).isNotNull();
            assertThat(model.listOfEnumExamples).isNotNull();
            assertThat(model.node).isNotNull();
        });
    }
}
