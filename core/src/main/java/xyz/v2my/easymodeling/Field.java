package xyz.v2my.easymodeling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Field {

    String name();

    double max() default Double.NaN;

    double min() default Double.NaN;

    double constant() default Double.NaN;

    boolean alphanumeric() default true;

    boolean alphabetic() default false;

    boolean numeric() default false;

    String string() default "";

    boolean now() default false;

    boolean past() default false;

    boolean future() default false;

    String before() default "";

    String after() default "";

    String datetime() default "";
}
