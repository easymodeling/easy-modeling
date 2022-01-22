package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class StreamModelTest {

    @Test
    void should_create_fields() {
        final StreamModel next = StreamModelModeler.next();

        assertNotNull(next);
        assertNotNull(next.streamOfStrings);
        assertNotNull(next.intStream);
        assertNotNull(next.streamOfIntStreams);
        assertNotNull(next.intStreamArray);
        assertNotNull(next.intStreamMatrix);
        assertNotNull(next.longStream);
        assertNotNull(next.streamOfLongStreams);
        assertNotNull(next.longStreamArray);
        assertNotNull(next.longStreamMatrix);
    }
}
