package xyz.v2my.modeler;

import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.SomeNestedModel;

class SomeNestedModelModelerTest {

    @Test
    void should_create_next() {
        final SomeNestedModel next = SomeNestedModelModeler.next();

        System.out.println(next);
    }

    @Test
    void should_create_builder() {
        final SomeNestedModel next = SomeNestedModelModeler.builder().build();

        System.out.println(next);
    }
}
