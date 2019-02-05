package io.github.iamtwang.timelogger.component;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;


@Documented
@Target({ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeLogger {

    Level level() default Level.DEBUG;

    String methodName() default "";

    int timeout() default -1;

    enum Level{
        TRACE, DEBUG, INFO, WARN, ERROR
    }

}
