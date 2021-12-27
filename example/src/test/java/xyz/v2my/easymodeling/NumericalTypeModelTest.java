package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumericalTypeModelTest {

    @Test
    void should_create_numerical_type_model() {
        final NumericalTypeModel model = EMNumericalTypeModel.builder().build();

        Assertions.assertEquals(11, model.getAnInt());
        Assertions.assertEquals(11, (short) model.getaShort());
    }
}
