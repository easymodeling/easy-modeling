package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ReflectionUtilInitializeModelTest {

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
    void should_throw_specific_exception_for_initializing_abstract_class() {
        final Throwable throwable = catchThrowable(() -> ReflectionUtil.createModelOf(AbstractClass.class));

        assertThat(throwable)
                .isInstanceOf(EasyModelingException.class)
                .hasMessage("cannot create model for AbstractClass");
    }

    @Test
    void should_throw_when_initializing_class_with_exceptional_constructor() {
        final Throwable throwable = catchThrowable(() -> ReflectionUtil.createModelOf(ClassWithExceptionalConstructor.class));

        assertThat(throwable)
                .isInstanceOf(EasyModelingException.class)
                .hasMessage("cannot create model for " + ClassWithExceptionalConstructor.class.getSimpleName())
                .hasStackTraceContaining("at " + ReflectionUtil.class.getName() + ".createModelOf(ReflectionUtil.java:");
    }

    @Test
    void should_throw_when_initializing_class_with_only_the_first_exceptional_constructor() {
        final Throwable throwable = catchThrowable(() -> {
            final ClassWithTheFirstExceptionalConstructor model = ReflectionUtil.createModelOf(ClassWithTheFirstExceptionalConstructor.class);

            assertThat(model.anInt).isEqualTo(1);
        });

        assertThat(throwable).isNull();
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

    public static class ClassWithExceptionalConstructor {

        private ClassWithExceptionalConstructor() {
            throw new RuntimeException();
        }
    }

    public static class ClassWithTheFirstExceptionalConstructor {

        public int anInt;

        private ClassWithTheFirstExceptionalConstructor() {
            throw new RuntimeException();
        }

        private ClassWithTheFirstExceptionalConstructor(int ignored) {
            this.anInt = 1;
        }

        private ClassWithTheFirstExceptionalConstructor(int ignoredFoo, int ignoredBar) {
            this.anInt = 2;
        }
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
