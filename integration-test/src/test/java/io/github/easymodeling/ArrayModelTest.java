package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayModelTest {

    @Test
    void should_generate_arrays() {
        final ArrayModel model = ArrayModelModeler.next();

        assertThat(model).isNotNull();
        assertThat(model.anIntArray).isNotNull().isNotEmpty();
        assertThat(model.anIntMatrix).isNotNull().isNotEmpty();
        assertThat(model.aByteArray).isNotNull().isNotEmpty();
        assertThat(model.aByteMatrix).isNotNull().isNotEmpty();
        assertThat(model.aShortArray).isNotNull().isNotEmpty();
        assertThat(model.aShortMatrix).isNotNull().isNotEmpty();
        assertThat(model.aLongArray).isNotNull().isNotEmpty();
        assertThat(model.aLongMatrix).isNotNull().isNotEmpty();
        assertThat(model.aFloatArray).isNotNull().isNotEmpty();
        assertThat(model.aFloatMatrix).isNotNull().isNotEmpty();
        assertThat(model.aDoubleArray).isNotNull().isNotEmpty();
        assertThat(model.aDoubleMatrix).isNotNull().isNotEmpty();
        assertThat(model.aBooleanArray).isNotNull().isNotEmpty();
        assertThat(model.aBooleanMatrix).isNotNull().isNotEmpty();
        assertThat(model.aCharArray).isNotNull().isNotEmpty();
        assertThat(model.aCharMatrix).isNotNull().isNotEmpty();
        assertThat(model.aStringArray).isNotNull().isNotEmpty();
        assertThat(model.aStringMatrix).isNotNull().isNotEmpty();
        assertThat(model.anPrimitiveIntArray).isNotNull().isNotEmpty();
        assertThat(model.anPrimitiveIntArrayMatrix).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveByteArray).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveByteArrayMatrix).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveShortArray).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveShortArrayMatrix).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveLongArray).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveLongArrayMatrix).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveFloatArray).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveFloatArrayMatrix).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveDoubleArray).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveDoubleArrayMatrix).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveBooleanArray).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveBooleanArrayMatrix).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveCharArray).isNotNull().isNotEmpty();
        assertThat(model.aPrimitiveCharArrayMatrix).isNotNull().isNotEmpty();
    }

    @Test
    void should_generate_array_with_size() {
        final ArrayModel model = ArrayModelModeler.next();

        assertThat(model.anIntArray).hasSizeGreaterThanOrEqualTo(2).hasSizeLessThan(5);
        assertThat(model.aByteMatrix.length).isEqualTo(8);
    }

    @Test
    void should_populate_constant_string_fields() {
        final ArrayModel model = ArrayModelModeler.next();

        assertThat(model.aStringArray).containsOnly("abc");
        for (final Short[] shortArray : model.aShortMatrix) {
            assertThat(shortArray).containsOnly((short) -8);
        }

        for (final float f : model.aFloatArray) {
            assertThat(f).isBetween(-1.1f, 5.5f);
        }
    }
}
