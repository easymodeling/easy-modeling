package io.github.easymodeling;

import org.junit.jupiter.api.Test;

@Model(type = NestingModel.class)
class NestingModelTest {

    @Test
    void should_populate_field_of_inner_class() {
        NestingModelModeler modeler = new NestingModelModeler();
    }
}
