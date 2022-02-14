package io.github.easymodeling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumericalTypeModelTest {

    @Test
    void should_create_numerical_type_model() {
        final NumericalTypeModel model = NumericalTypeModelModeler.builder().build();

        Assertions.assertEquals(11, model.getAnInt());
        Assertions.assertEquals(11, (short) model.getaShort());
    }
}
