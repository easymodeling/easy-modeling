package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionUtilTest {

    @Test
    void should_set_fields() throws NoSuchFieldException, IllegalAccessException {
        final SomeClass someClass = new SomeClass();

        ReflectionUtil.setField(someClass, "anInt", 123);
        ReflectionUtil.setField(someClass, "bigDecimal", BigDecimal.valueOf(123));

        assertThat(someClass.getAnInt()).isEqualTo(123);
        assertThat(someClass.getBigDecimal()).isEqualTo(BigDecimal.valueOf(123));
    }

    @Test
    void should_get_fields() {
        final SomeClass someClass = new SomeClass();

        someClass.setAnInt(123);
        someClass.setBigDecimal(BigDecimal.valueOf(123));

        assertThat(ReflectionUtil.getField(someClass, "anInt")).isEqualTo(123);
        assertThat(ReflectionUtil.getField(someClass, "bigDecimal")).isEqualTo(BigDecimal.valueOf(123));
    }

    @Nested
    class TestCreateModel {

        @Test
        void should_create_model_and_set_default_values() throws InvocationTargetException, InstantiationException, IllegalAccessException {
            final SomeClass someClass = ReflectionUtil.createModelOf(SomeClass.class);

            assertThat(someClass.getAnInt()).isEqualTo(0);
            assertThat(someClass.getALong()).isEqualTo(0);
            assertThat(someClass.getADouble()).isEqualTo(0);
            assertThat(someClass.getAFloat()).isEqualTo(0);
            assertThat(someClass.getAShort()).isEqualTo((short) 0);
            assertThat(someClass.getAByte()).isEqualTo((byte) 0);
            assertThat(someClass.isABoolean()).isEqualTo(false);
            assertThat(someClass.getAChar()).isEqualTo('\u0000');
            assertThat(someClass.getBigDecimal()).isNull();
        }

        @Test
        void should_create_model_with_only_private_constructor() throws InvocationTargetException, InstantiationException, IllegalAccessException {
            ReflectionUtil.createModelOf(ClassWithPrivateConstructor.class);
        }
    }

    public static class ClassWithPrivateConstructor {

        private ClassWithPrivateConstructor(int anInt) {
            this.anInt = anInt;
        }

        private int anInt;

    }

    public static class SomeClass {

        private int anInt;

        private long aLong;

        private double aDouble;

        private float aFloat;

        private short aShort;

        private byte aByte;

        private boolean aBoolean;

        private char aChar;

        private BigDecimal bigDecimal;

        public int getAnInt() {
            return anInt;
        }

        public void setAnInt(int anInt) {
            this.anInt = anInt;
        }

        public long getALong() {
            return aLong;
        }

        public double getADouble() {
            return aDouble;
        }

        public float getAFloat() {
            return aFloat;
        }

        public short getAShort() {
            return aShort;
        }

        public byte getAByte() {
            return aByte;
        }

        public boolean isABoolean() {
            return aBoolean;
        }

        public char getAChar() {
            return aChar;
        }

        public BigDecimal getBigDecimal() {
            return bigDecimal;
        }

        public void setBigDecimal(BigDecimal bigDecimal) {
            this.bigDecimal = bigDecimal;
        }
    }

}
