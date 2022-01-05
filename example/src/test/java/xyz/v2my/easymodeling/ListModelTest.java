package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ListModelTest {

    @Test
    void should_populate_list_model() {
        final ListModel model = EMListModel.next();

        assertNotNull(model);
        assertNotNull(model.listOfInts);
        assertNull(model.listOfPrimitiveInts);
    }
}
