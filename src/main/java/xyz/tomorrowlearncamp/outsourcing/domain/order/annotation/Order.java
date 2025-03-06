package xyz.tomorrowlearncamp.outsourcing.domain.order.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {
}
