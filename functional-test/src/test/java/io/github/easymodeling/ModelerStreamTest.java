package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Model(type = Student.class)
public class ModelerStreamTest {

    @Test
    void should_stream_be_infinite() {
        final Stream<Student> studentStream = StudentModeler.stream();

        assertThat(studentStream.spliterator().estimateSize()).isEqualTo(Long.MAX_VALUE);
    }
}
