package io.github.easymodeling;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ReflectionUtilTest {

    @Nested
    class TestSetFields {

        @Test
        void should_set_fields() {
            final SomeClass someClass = new SomeClass();

            final String className = SomeClass.class.getCanonicalName();
            ReflectionUtil.setField(someClass, className + "#anInt", 123);
            ReflectionUtil.setField(someClass, className + "#bigDecimal", BigDecimal.valueOf(123));

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
    class TestSetDerivedFields {

        @Test
        void should_set_derived_fields() {
            final SomeDerived derived = new SomeDerived();

            final String className = SomeDerived.class.getCanonicalName();

            ReflectionUtil.setField(derived, className + "#derivedInt", 123);
            ReflectionUtil.setField(derived, className + "#derivedDouble", 1.23d);

            assertThat(derived.derivedInt).isEqualTo(123);
            assertThat(derived.derivedDouble).isEqualTo(1.23d);
        }

        @Test
        void should_set_base_fields() {
            final SomeDerived derived = new SomeDerived();

            final String className = SomeBase.class.getCanonicalName();

            ReflectionUtil.setField(derived, className + "#baseInt", 456);
            ReflectionUtil.setField(derived, className + "#baseInteger", 789);
            ReflectionUtil.setField(derived, className + "#baseString", "some String");

            assertThat(derived.baseInt).isEqualTo(456);
            assertThat(derived.baseInteger).isEqualTo(789);
            assertThat(derived.baseString).isEqualTo("some String");
        }

        @Test
        void should_set_hidden_float_of_both_base_fields_and_derived_fields() {
            final SomeDerived derived = new SomeDerived();

            final String baseClassName = SomeBase.class.getCanonicalName();
            final String derivedClassName = SomeDerived.class.getCanonicalName();

            ReflectionUtil.setField(derived, baseClassName + "#hiddenFloat", 1.009f);
            ReflectionUtil.setField(derived, derivedClassName + "#hiddenFloat", 9.001f);

            assertThat(((SomeBase) derived).hiddenFloat).isEqualTo(1.009f);
            assertThat(derived.hiddenFloat).isEqualTo(9.001f);
        }

        @Test
        void should_set_hidden_big_decimal_of_both_base_fields_and_derived_fields() {
            final SomeDerived derived = new SomeDerived();

            final String baseClassName = SomeBase.class.getCanonicalName();
            final String derivedClassName = SomeDerived.class.getCanonicalName();

            ReflectionUtil.setField(derived, baseClassName + "#hiddenBigDecimal", BigDecimal.ONE);
            ReflectionUtil.setField(derived, derivedClassName + "#hiddenBigDecimal", BigDecimal.TEN);

            assertThat(((SomeBase) derived).hiddenBigDecimal).isEqualTo(BigDecimal.ONE);
            assertThat(derived.hiddenBigDecimal).isEqualTo(BigDecimal.TEN);
        }

        @Test
        void should_set_hidden_fields_of_both_base_fields_and_derived_fields() {
            final SomeDerived derived = new SomeDerived();

            final String baseClassName = SomeBase.class.getCanonicalName();
            final String derivedClassName = SomeDerived.class.getCanonicalName();

            ReflectionUtil.setField(derived, baseClassName + "#hiddenTyped", "BaseHiddenTyped");
            ReflectionUtil.setField(derived, derivedClassName + "#hiddenTyped", 'D');

            assertThat(((SomeBase) derived).hiddenTyped).isInstanceOf(String.class).isEqualTo("BaseHiddenTyped");
            assertThat(derived.hiddenTyped).isInstanceOf(Character.class).isEqualTo('D');
        }

        @Test
        void should_throw_exception_if_qualified_field_name_is_not_complete() {
            Stream.of(
                    "SomeBase#hiddenTyped",
                    SomeDerived.class.getSimpleName() + "#hiddenTyped",
                    "#hiddenTyped",
                    SomeDerived.class.getSimpleName() + "#",
                    SomeDerived.class.getSimpleName() + "hiddenTyped"
            ).forEach(this::should_throw_exception_for_field_name);
        }

        private void should_throw_exception_for_field_name(String qualifiedFieldName) {
            final SomeDerived derived = new SomeDerived();

            final Throwable throwable = catchThrowable(() -> ReflectionUtil.setField(derived, qualifiedFieldName, 'D'));

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

    public static class SomeBase {

        public int baseInt;

        public Integer baseInteger;

        public String baseString;

        public float hiddenFloat;

        public BigDecimal hiddenBigDecimal;

        public String hiddenTyped;
    }

    public static class SomeDerived extends SomeBase {

        public int derivedInt;

        public Double derivedDouble;

        public float hiddenFloat;

        public BigDecimal hiddenBigDecimal;

        public Character hiddenTyped;
    }

}
