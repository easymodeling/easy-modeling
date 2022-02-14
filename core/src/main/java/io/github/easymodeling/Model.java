package io.github.easymodeling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Repeatable(Models.class)
public @interface Model {

    Class<?> type();

    Field[] fields() default {};
}
