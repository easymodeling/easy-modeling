package xyz.v2my.easymodeling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Field {

    String name();

    long max() default Long.MAX_VALUE;

    long min() default 0;

    double constant() default Double.NaN;
}
