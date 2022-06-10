package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CourseStudentTest {

    @Test
    void should_generate_course() {
        final Course course = CourseModeler.next();

        assertThat(course).isNotNull();
    }

    @Test
    void should_generate_student() {
        final Student student = StudentModeler.next();

        assertThat(student).isNotNull();
    }
}
