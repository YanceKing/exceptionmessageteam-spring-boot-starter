package com.yance.anno;

import java.lang.annotation.*;

/**
 * 异常监听 ANNO
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ExceptionListener {

	String value() default "";

}
