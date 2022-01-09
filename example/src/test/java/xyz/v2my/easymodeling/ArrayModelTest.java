package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayModelTest {

    @Test
    void should_generate_arrays() {
        final ArrayModel model = ArrayModelModeler.next();

        assertNotNull(model);
        assertNotNull(model.anIntArray);
        assertTrue(model.anIntArray.length > 0);

        assertNotNull(model.anIntMatrix);
        assertTrue(model.anIntMatrix.length > 0);

        assertNotNull(model.aByteArray);
        assertTrue(model.aByteArray.length > 0);

        assertNotNull(model.aByteMatrix);
        assertTrue(model.aByteMatrix.length > 0);

        assertNotNull(model.aShortArray);
        assertTrue(model.aShortArray.length > 0);

        assertNotNull(model.aShortMatrix);
        assertTrue(model.aShortMatrix.length > 0);

        assertNotNull(model.aLongArray);
        assertTrue(model.aLongArray.length > 0);

        assertNotNull(model.aLongMatrix);
        assertTrue(model.aLongMatrix.length > 0);

        assertNotNull(model.aFloatArray);
        assertTrue(model.aFloatArray.length > 0);

        assertNotNull(model.aFloatMatrix);
        assertTrue(model.aFloatMatrix.length > 0);

        assertNotNull(model.aDoubleArray);
        assertTrue(model.aDoubleArray.length > 0);

        assertNotNull(model.aDoubleMatrix);
        assertTrue(model.aDoubleMatrix.length > 0);

        assertNotNull(model.aBooleanArray);
        assertTrue(model.aBooleanArray.length > 0);

        assertNotNull(model.aBooleanMatrix);
        assertTrue(model.aBooleanMatrix.length > 0);

        assertNotNull(model.aCharArray);
        assertTrue(model.aCharArray.length > 0);

        assertNotNull(model.aCharMatrix);
        assertTrue(model.aCharMatrix.length > 0);

        assertNotNull(model.aStringArray);
        assertTrue(model.aStringArray.length > 0);

        assertNotNull(model.aStringMatrix);
        assertTrue(model.aStringMatrix.length > 0);

        assertNotNull(model.anPrimitiveIntArray);
        assertTrue(model.anPrimitiveIntArray.length > 0);

        assertNotNull(model.anPrimitiveIntArrayMatrix);
        assertTrue(model.anPrimitiveIntArrayMatrix.length > 0);

        assertNotNull(model.aPrimitiveByteArray);
        assertTrue(model.aPrimitiveByteArray.length > 0);

        assertNotNull(model.aPrimitiveByteArrayMatrix);
        assertTrue(model.aPrimitiveByteArrayMatrix.length > 0);

        assertNotNull(model.aPrimitiveShortArray);
        assertTrue(model.aPrimitiveShortArray.length > 0);

        assertNotNull(model.aPrimitiveShortArrayMatrix);
        assertTrue(model.aPrimitiveShortArrayMatrix.length > 0);

        assertNotNull(model.aPrimitiveLongArray);
        assertTrue(model.aPrimitiveLongArray.length > 0);

        assertNotNull(model.aPrimitiveLongArrayMatrix);
        assertTrue(model.aPrimitiveLongArrayMatrix.length > 0);

        assertNotNull(model.aPrimitiveFloatArray);
        assertTrue(model.aPrimitiveFloatArray.length > 0);

        assertNotNull(model.aPrimitiveFloatArrayMatrix);
        assertTrue(model.aPrimitiveFloatArrayMatrix.length > 0);

        assertNotNull(model.aPrimitiveDoubleArray);
        assertTrue(model.aPrimitiveDoubleArray.length > 0);

        assertNotNull(model.aPrimitiveDoubleArrayMatrix);
        assertTrue(model.aPrimitiveDoubleArrayMatrix.length > 0);

        assertNotNull(model.aPrimitiveBooleanArray);
        assertTrue(model.aPrimitiveBooleanArray.length > 0);

        assertNotNull(model.aPrimitiveBooleanArrayMatrix);
        assertTrue(model.aPrimitiveBooleanArrayMatrix.length > 0);

        assertNotNull(model.aPrimitiveCharArray);
        assertTrue(model.aPrimitiveCharArray.length > 0);

        assertNotNull(model.aPrimitiveCharArrayMatrix);
        assertTrue(model.aPrimitiveCharArrayMatrix.length > 0);

    }

    @Test
    void should_generate_array_with_size() {
        final ArrayModel model = ArrayModelModeler.next();

        assertTrue(model.anIntArray.length >= 2);
        assertTrue(model.anIntArray.length < 5);

        assertEquals(8, model.aByteMatrix.length);
    }

    @Test
    void should_populate_constant_string_fields() {
        final ArrayModel model = ArrayModelModeler.next();

        for (final String s : model.aStringArray) {
            assertEquals("abc", s);
        }

        for (final Short[] shortArray : model.aShortMatrix) {
            for (final Short s : shortArray) {
                assertEquals((short) -8, s);
            }
        }

        for (final float f : model.aFloatArray) {
            System.out.println(f);
            assertTrue(f >= -1.1 && f < 5.5);
        }
    }
}
