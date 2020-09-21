package io.github.jasonkayzk.util.nio.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Transaction {
    String value() default "true";
}
