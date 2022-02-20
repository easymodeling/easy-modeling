package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StreamModelTest {

    @Test
    void should_create_fields() {
        final StreamModel next = StreamModelModeler.next();

        assertThat(next).isNotNull();
        assertThat(next.streamOfStrings).isNotNull();
        assertThat(next.intStream).isNotNull();
        assertThat(next.streamOfIntStreams).isNotNull();
        assertThat(next.intStreamArray).isNotNull();
        assertThat(next.intStreamMatrix).isNotNull();
        assertThat(next.longStream).isNotNull();
        assertThat(next.streamOfLongStreams).isNotNull();
        assertThat(next.longStreamArray).isNotNull();
        assertThat(next.longStreamMatrix).isNotNull();
        assertThat(next.doubleStream).isNotNull();
        assertThat(next.streamOfDoubleStreams).isNotNull();
        assertThat(next.doubleStreamArray).isNotNull();
        assertThat(next.doubleStreamMatrix).isNotNull();
    }
}
