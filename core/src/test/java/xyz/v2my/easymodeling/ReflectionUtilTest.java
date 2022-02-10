package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ReflectionUtilTest {

    @Nested
    class TestSetFields {

        @Test
        void should_set_fields() {
            final SomeClass someClass = new SomeClass();

            ReflectionUtil.setField(someClass, "anInt", 123);
            ReflectionUtil.setField(someClass, "bigDecimal", BigDecimal.valueOf(123));

            assertThat(someClass.getAnInt()).isEqualTo(123);
            assertThat(someClass.getBigDecimal()).isEqualTo(BigDecimal.valueOf(123));
        }

        @Test
        void should_throw_specific_exception_when_setting_illegal_argument() {
            final SomeClass someClass = new SomeClass();

            final Throwable throwable = catchThrowable(() -> ReflectionUtil.setField(someClass, "anInt", "123"));

            assertThat(throwable).isInstanceOf(EasyModelingException.class);
        }

        @Test
        void should_throw_specific_exception_when_accessing_unknown_field() {
            final SomeClass someClass = new SomeClass();

            final Throwable throwable = catchThrowable(() -> ReflectionUtil.setField(someClass, "someUnknownFieldName", "123"));

            assertThat(throwable).isInstanceOf(EasyModelingException.class);
        }
    }

    @Nested
    class TestGetFields {

        @Test
        void should_get_fields() {
            final SomeClass someClass = new SomeClass();
            someClass.setAnInt(123);
            someClass.setBigDecimal(BigDecimal.valueOf(123));

            final Object anInt = ReflectionUtil.getField(someClass, "anInt");
            final Object bigDecimal = ReflectionUtil.getField(someClass, "bigDecimal");

            assertThat(anInt).isEqualTo(123);
            assertThat(bigDecimal).isEqualTo(BigDecimal.valueOf(123));
        }

        @Test
        void should_throw_specific_exception() {
            final SomeClass someClass = new SomeClass();
            someClass.setAnInt(123);
            someClass.setBigDecimal(BigDecimal.valueOf(123));

            final Throwable throwable = catchThrowable(() -> ReflectionUtil.getField(someClass, "someUnknownFieldName"));

            assertThat(throwable).isInstanceOf(EasyModelingException.class);
        }
    }

    @Nested
    class TestCreateModel {

        @Test
        void should_create_model_and_set_default_values() {
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
        void should_create_model_with_only_private_constructor() {
            final ClassWithPrivateConstructor model = ReflectionUtil.createModelOf(ClassWithPrivateConstructor.class);

            assertThat(model).isNotNull();
        }

        @Test
        void should_throw_specific_exception() {
            final Throwable throwable = catchThrowable(() -> ReflectionUtil.createModelOf(AbstractClass.class));

            assertThat(throwable).isInstanceOf(EasyModelingException.class);
        }
    }

    public static abstract class AbstractClass {

        private AbstractClass(int anInt) {
            this.anInt = anInt;
        }

        private int anInt;

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
